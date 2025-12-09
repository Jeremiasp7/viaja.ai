package br.com.planejaai.framework.strategy;

import org.springframework.ai.chat.client.ChatClient;

public abstract class ConceptDescriptionService {

  protected final ChatClient chatClient;

  protected ConceptDescriptionService(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  public String recommendConcept(String userQuery) {
    String prompt = generatePrompt(userQuery);
    return chatClient.prompt().user(prompt).call().content();
  }

  public abstract String generatePrompt(String query);
}
