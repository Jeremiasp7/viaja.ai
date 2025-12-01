package br.com.viajaai.viajaai.llm;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.ai.chat.client.ChatClient;

@Component
public class SpringChatClientAdapter implements LlmAdapter {

    private final ChatClient chatClient;

    public SpringChatClientAdapter(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String generate(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public <T> T generateEntity(String prompt, ParameterizedTypeReference<T> typeRef) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(typeRef);
    }
}
