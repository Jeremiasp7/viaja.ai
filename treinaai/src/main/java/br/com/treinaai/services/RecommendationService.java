package br.com.treinaai.services;

import java.util.List;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import br.com.planejaai.framework.strategy.ObjectRecommendationService;
import org.springframework.ai.chat.client.ChatClient;

@Service
public class RecommendationService extends ObjectRecommendationService<String> {

    public RecommendationService(ChatClient.Builder chatClientBuilder) {
        super(chatClientBuilder);
    }

    public List<String> recommendObjects(UUID planId, String context, Integer count) {
        String prompt = buildPrompt(planId, context, count == null ? 5 : count);
        try {
            return this.executeRecommendation(prompt, new ParameterizedTypeReference<List<String>>() {
            });
        } catch (Exception ex) {
            return fallbackRecommendations(context, count == null ? 5 : count);
        }
    }

    private List<String> fallbackRecommendations(String context, int count) {
        java.util.ArrayList<String> recs = new java.util.ArrayList<>();
        for (int i = 1; i <= Math.max(1, count); i++) {
            recs.add("Exercício recomendado " + i + " baseado no contexto: " + (context == null ? "geral" : context));
        }
        return recs;
    }

    private String buildPrompt(UUID planId, String context, int count) {
        StringBuilder sb = new StringBuilder();
        sb.append("Gere uma lista de ").append(count).append(" itens de recursos/objetos úteis para o plano ").append(planId).append(". ");
        if (context != null && !context.isBlank()) sb.append("Contexto: ").append(context).append(". ");
        sb.append("Responda em português com itens separados por vírgula ou nova linha, sem explicações longas.");
        return sb.toString();
    }
}
