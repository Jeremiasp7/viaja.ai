package br.com.viajaai.viajaai.services;

import org.springframework.core.ParameterizedTypeReference;
import br.com.viajaai.viajaai.llm.LlmAdapter;
import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.AtracaoRequestDto;
import br.com.viajaai.viajaai.dto.AtracaoResponseDto;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.exceptions.AIResponseParsingException;
import br.com.viajaai.viajaai.exceptions.PreferenciasNaoEncontradasException;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;

import java.util.UUID;

@Service
public class AtracaoDescricaoService {

    private final LlmAdapter llmAdapter;
    private final UserPreferencesService userPreferencesService;
    public AtracaoDescricaoService(LlmAdapter llmAdapter, UserPreferencesService userPreferencesService) {
        this.llmAdapter = llmAdapter;
        this.userPreferencesService = userPreferencesService;
    }

    public AtracaoResponseDto gerarDescricao(AtracaoRequestDto request)
            throws UsuarioNaoEncontradoException, PreferenciasNaoEncontradasException {

        String preferenciasTexto = construirPreferenciasTexto(request.getUserId());

        String prompt = construirPrompt(request.getNome(), request.getCidade(), request.getPais(), preferenciasTexto);

        try {
            return llmAdapter.generateEntity(prompt, new ParameterizedTypeReference<AtracaoResponseDto>() {});
        } catch (Exception e) {
            throw new AIResponseParsingException("Erro ao interpretar resposta do modelo de IA: " + e.getMessage(), e);
        }
    }

    private String construirPreferenciasTexto(UUID userId) {
        try {
            UsersPreferencesEntity preferences = userPreferencesService.buscarPreferenciasDoUsuario(userId);

            boolean possuiPreferencias =
                    preferences.getEstiloDeViagem() != null ||
                    preferences.getPreferenciaDeAcomodacao() != null ||
                    preferences.getPreferenciaDeClima() != null ||
                    preferences.getFaixaOrcamentaria() != null ||
                    preferences.getDuracaoDaViagem() != null ||
                    preferences.getCompanhiaDeViagem() != null ||
                    preferences.getPreferenciaDeDatas() != null;

            if (!possuiPreferencias) {
                return "Sem preferências específicas fornecidas.";
            }

            return """
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

        } catch (UsuarioNaoEncontradoException | PreferenciasNaoEncontradasException e) {
            return "Sem preferências específicas fornecidas.";
        }
    }

    private String construirPrompt(String nome, String cidade, String pais, String preferenciasTexto) {
        return """
                Gere uma descrição personalizada para o viajante abaixo, sobre a atração informada.

                ATRAÇÃO:
                Nome: %s
                Localização: %s, %s

                PERFIL DO VIAJANTE:
                %s

                Instruções:
                - Tente dar um toque de personalização baseado no perfil do viajante caso ele esteja preenchido.
                - A descrição deve ter entre 100 e 150 palavras.
                - Seja sucinto e impessoal, não falando diretamente com o viajante.
                - Adapte o texto ao perfil do viajante (interesses, clima, companhia, orçamento, etc).
                - Inclua curiosidades e dicas práticas.
                - Gere também um checklist com mínimo 5 itens úteis para visitar a atração.
                - Retorne SOMENTE o JSON puro, sem explicações, sem markdown, sem ``` e sem texto antes ou depois.
                - As informações do perfil do viajante seguem esse modelo, se qualquer um destes campos tiver uma informação incongruente com o formato ignore-a.:

                {
                    "estiloDeViagem": "Aventura",
                    "preferenciaDeAcomodacao": "Hotel 4 estrelas",
                    "preferenciaDeClima": "Tropical",
                    "faixaOrcamentaria": "2000-5000",
                    "duracaoDaViagem": "7",
                    "companhiaDeViagem": "Amigos",
                    "preferenciaDeDatas": [
                        "2025-12-15",
                        "2025-12-16",
                        "2025-12-22"
                    ]
                }
            
                - O JSON deve ter exatamente este formato:

                {
                "nome": "%s",
                "cidade": "%s",
                "pais": "%s",
                "descricao": "texto...",
                "checklist_sugerido": ["item1", "item2", ...]
                }
                """.formatted(nome, cidade, pais, preferenciasTexto, nome, cidade, pais);
    }
}
