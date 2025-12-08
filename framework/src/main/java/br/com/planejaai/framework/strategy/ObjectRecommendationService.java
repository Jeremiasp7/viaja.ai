package br.com.planejaai.framework.strategy;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;

import br.com.planejaai.framework.exception.AIResponseParsingException;

public abstract class ObjectRecommendationService<T> implements LlmStrategy {

    protected final ChatClient chatClient;

    protected ObjectRecommendationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    protected List<T> executeRecommendation(String prompt, ParameterizedTypeReference<List<T>> responseType) {
        try {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .entity(responseType);
        } catch (Exception e) {
            throw new AIResponseParsingException("Erro ao processar resposta da IA: " + e.getMessage(), e);
        }
    }

    @Override
    public Object execute(String input) {
        throw new UnsupportedOperationException("Utilize os métodos espcecificos.");
    }
}