package br.com.planejaai.framework.strategy;

import org.springframework.ai.chat.client.ChatClient;

public abstract class ConceptDescriptionService implements LlmStrategy<String> {

  protected final ChatClient chatClient;

  protected ConceptDescriptionService(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  public String recommendConcept(String userQuery) {
    String prompt = generatePrompt(userQuery);
    return chatClient.prompt().user(prompt).call().content();
  }

  public abstract String generatePrompt(String query);

  @Override
  public String execute(String input) {
    return recommendConcept(input);
  }
}
