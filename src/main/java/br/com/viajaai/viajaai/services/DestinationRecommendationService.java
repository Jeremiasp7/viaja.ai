package br.com.viajaai.viajaai.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference; 
import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.RecommendDestinationRequestDto;
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

		public List<RecommendedDestinationDto> recommendForUser(UUID userId, RecommendDestinationRequestDto recommendDestinationRequestDto) {
				List<DestinationEntity> pastTravels = destinationRepository.findByUserId(userId);
				
				String prompt = buildRecommendationPrompt(pastTravels, recommendDestinationRequestDto);

				try {
						return chatClient.prompt()
										.user(prompt)
										.call()
										.entity(new ParameterizedTypeReference<List<RecommendedDestinationDto>>() {});
				} catch (Exception e) {
						throw new AIResponseParsingException("Erro ao interpretar resposta do modelo de IA: " + e.getMessage(), e);
				}
		}

		private String buildRecommendationPrompt(List<DestinationEntity> pastTravels, RecommendDestinationRequestDto request) {
				String travelContext = buildTravelContext(pastTravels);
				String userPromptContext = buildUserPromptContext(request);
				
				return """
								Você é um assistente de viagens personalizado.
								%s
								%s
								
								INSTRUÇÕES DE PRIORIDADE:
								- Se o usuário fez um pedido específico (user prompt), IGNORE o histórico de viagens e foque EXCLUSIVAMENTE no que foi solicitado
								- Se não há user prompt, baseie as recomendações no histórico de viagens
								- Se não há histórico nem user prompt, sugira destinos clássicos e variados
								
								FORMATO DE SAÍDA REQUERIDO (JSON):
								[
									 {
										 "city": "Nome da cidade",
										 "country": "País",
										 "mustVisitPlaces": [
											 {
												 "name": "Nome do local turístico real e existente",
												 "latitude": número decimal,
												 "longitude": número decimal
											 }
										 ]
									 }
								]

								REGRAS CRÍTICAS:
								- Retorne SOMENTE JSON bruto, sem markdown, sem comentários, sem delimitadores
								- Sempre retorne exatamente 3 destinos
								- Coordenadas devem ser precisas e corresponder aos locais reais
								- Nomes de cidades, países e pontos turísticos devem ser verificáveis
								""".formatted(travelContext, userPromptContext);
		}

		private String buildTravelContext(List<DestinationEntity> pastTravels) {
				if (pastTravels.isEmpty()) {
						return "Histórico de viagens: O usuário ainda não possui histórico de viagens.";
				}
				
				String destinations = pastTravels.stream()
								.map(d -> d.getCity() + ", " + d.getCountry())
								.collect(Collectors.joining("; "));
								
				return "Histórico de viagens do usuário: " + destinations + ".";
		}

		private String buildUserPromptContext(RecommendDestinationRequestDto request) {
				if (!request.hasUserPrompt()) {
						return "Pedido específico do usuário: Nenhum pedido específico foi feito.";
				}
				
				return "Pedido específico do usuário: \"" + request.getUserPrompt() + "\" - " +
							 "DÊ PRIORIDADE MÁXIMA A ESTE PEDIDO E IGNORE O HISTÓRICO DE VIAGENS SE FOR CONTRADITÓRIO.";
		}
}

