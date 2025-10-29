package br.com.viajaai.viajaai.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference; 
import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.RecommendedDestinationDto;
import br.com.viajaai.viajaai.entities.DestinationEntity;
import br.com.viajaai.viajaai.exceptions.AIResponseParsingException;
import br.com.viajaai.viajaai.repositories.DestinationRepository;

import lombok.Data;

@Service
public class DestinationRecommendationService {

    private final DestinationRepository destinationRepository;
    private final ChatClient chatClient;

    @Autowired
    public DestinationRecommendationService(DestinationRepository destinationRepository, ChatClient.Builder chatClientBuilder) {
        this.destinationRepository = destinationRepository;
        this.chatClient = chatClientBuilder.build();
    }

    public List<RecommendedDestinationDto> recommendForuser(UUID userId) {
        List<DestinationEntity> passadas = destinationRepository.findByUserId(userId);

        String contexto = passadas.isEmpty()
                ? "O usuário ainda não viajou para lugar nenhum."
                : passadas.stream()
                        .map(d -> d.getCity() + ", " + d.getCountry())
                        .collect(Collectors.joining("; "));

        String prompt = """
                Você é um assistente de viagens personalizado.
                O usuário já visitou os seguintes destinos: %s.

                Com base nisso, sugira 3 novos destinos que combinem com o perfil do usuário
                ou que possam expandir suas experiências de viagem.

                Para cada destino sugerido, forneça os seguintes campos em formato JSON:
                [
                   {
                     "city": "Nome da cidade",
                     "country": "País",
                     "mustVisitPlaces": [
                       {
                         "name": "Nome do local turístico ou ponto de interesse",
                         "latitude": número,
                         "longitude": número
                       }
                     ]
                   }
                ]

                Importante:
                - Retorne **somente JSON bruto**, sem formatação Markdown, sem comentários, sem ```json``` ou ``` delimitadores.
                - As coordenadas (latitude e longitude) devem ser plausíveis.
                - Escolha locais que realmente combinem com os interesses inferidos do usuário.
                - Se o usuário nunca viajou, sugira destinos clássicos e variados.
                """.formatted(contexto);

        try {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .entity(new ParameterizedTypeReference<List<RecommendedDestinationDto>>() {});
        } catch (Exception e) {
            throw new AIResponseParsingException("Erro ao interpretar resposta do modelo de IA: " + e.getMessage(), e);
        }
    }
}

