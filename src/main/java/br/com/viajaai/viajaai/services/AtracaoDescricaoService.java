package br.com.viajaai.viajaai.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.AtracaoResponseDto;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AtracaoDescricaoService {

    private final ChatClient chatClient;
    private final UserPreferencesService userPreferencesService;

    @Value("${geoapify.api.key}")
    private String apiKey;

    @Value("${geoapify.base.url}")
    private String baseUrl;

    public AtracaoDescricaoService(ChatClient.Builder chatClientBuilder, UserPreferencesService userPreferencesService) {
        this.chatClient = chatClientBuilder.build();
        this.userPreferencesService = userPreferencesService;
    }

    public AtracaoResponseDto gerarDescricao(UUID userId, String nome, String cidade, String pais) throws UsuarioNaoEncontradoException {

        UsersPreferencesEntity preferences = null;
        boolean possuiPreferencias = false;

        try {
                preferences = userPreferencesService.buscarPreferenciasDoUsuario(userId);

                possuiPreferencias =
                        preferences.getEstiloDeViagem() != null ||
                        preferences.getPreferenciaDeAcomodacao() != null ||
                        preferences.getPreferenciaDeClima() != null ||
                        preferences.getFaixaOrcamentaria() != null ||
                        preferences.getDuracaoDaViagem() != null ||
                        preferences.getCompanhiaDeViagem() != null ||
                        preferences.getPreferenciaDeDatas() != null;

        } catch (UsuarioNaoEncontradoException e) {
                possuiPreferencias = false;
        }

        String preferenciasTexto;

        if(!possuiPreferencias) {
                preferenciasTexto = "Sem preferências específicas fornecidas.";
        }else{
                preferenciasTexto = """
                Estilo: %s
                Hospedagem: %s
                Clima: %s
                Orçamento: %s
                Duração: %s dias
                Companhia: %s
                Datas: %s
                """.formatted(
                        preferences.getEstiloDeViagem(),
                        preferences.getPreferenciaDeAcomodacao(),
                        preferences.getPreferenciaDeClima(),
                        preferences.getFaixaOrcamentaria(),
                        preferences.getDuracaoDaViagem(),
                        preferences.getCompanhiaDeViagem(),
                        preferences.getPreferenciaDeDatas()
                );
        }
        

        String prompt = """
                Gere uma descrição personalizada para o viajante abaixo, sobre a atração informada.

                ATRAÇÃO:
                Nome: %s
                Localização: %s, %s

                PERFIL DO VIAJANTE:
                %s

                Instruções:
                - Tente dar um toque de personalização baseado no perfil do viajante caso ele esteja preenchido antes das instruções.
                - A descrição deve ter entre 100 e 150 palavras
                - Seja sucinto e impessoal não falando diretamente com o viajante
                - Adapte o texto ao perfil do viajante (interesses, clima, companhia, orçamento, etc)
                - Inclua curiosidades e dicas práticas
                - Gere também um checklist com mínimo 5 itens úteis para visitar a atração
                - Se não conseguir gerar o conteúdo, retorne um JSON vazio com as mesmas chaves.
                - NÃO use blocos de código markdown.
                - NÃO escreva texto fora do JSON.
                - Retorne SOMENTE o JSON puro, sem explicações, sem markdown, sem ``` e sem texto antes ou depois. A resposta deve ser estritamente um JSON válido e exatamente neste formato:
                

                {
                  "descricao": "texto...",
                  "checklist": ["item1", "item2", ...]
                }
                """
                .formatted(nome, cidade, pais, preferenciasTexto);

        String respostaLLM = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        
        
        respostaLLM = respostaLLM
        .replaceAll("```json", "")
        .replaceAll("```", "")
        .trim();

        Pattern pattern = Pattern.compile("\\{[\\s\\S]*}", Pattern.DOTALL);
        java.util.regex.Matcher matcher = pattern.matcher(respostaLLM);
        if (matcher.find()) {
                respostaLLM = matcher.group(0);
        } else {
                throw new RuntimeException("Nenhum JSON válido encontrado na resposta do modelo: " + respostaLLM);
        }

        JsonNode jsonResponse;
        try {
            jsonResponse = new ObjectMapper().readTree(respostaLLM);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao interpretar JSON da resposta do modelo: " + respostaLLM);
        }

        String descricaoGerada = jsonResponse.path("descricao").asText();
        List<String> checklist = new ArrayList<>();
        jsonResponse.path("checklist").forEach(item -> checklist.add(item.asText()));

        return AtracaoResponseDto.builder()
                .nome(nome)
                .cidade(cidade)
                .pais(pais)
                .descricao(descricaoGerada)
                .checklistSugerido(checklist)
                .build();
    }
}
