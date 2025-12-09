package br.com.planejaai.framework.strategy;

import br.com.planejaai.framework.exception.AIResponseParsingException;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;

public abstract class ObjectRecommendationService<T> implements LlmStrategy<java.util.List<T>> {

  protected final ChatClient chatClient;

  protected ObjectRecommendationService(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  protected List<T> executeRecommendation(
      String prompt, ParameterizedTypeReference<List<T>> responseType) {
    try {
      return chatClient.prompt().user(prompt).call().entity(responseType);
    } catch (Exception e) {
      throw new AIResponseParsingException(
          "Erro ao processar resposta da IA: " + e.getMessage(), e);
    }
  }

  @Override
  public java.util.List<T> execute(String input) {
    return executeRecommendation(input, new ParameterizedTypeReference<List<T>>() {});
  }
}
