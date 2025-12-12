package br.com.treinaai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/llm")
public class LlmTestController {

  private final ChatClient chatClient;

  public LlmTestController(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  @GetMapping("/test")
  public ResponseEntity<String> testLlm(@RequestParam(defaultValue = "Oi, como você está?") String prompt) {
    try {
      String response = chatClient.prompt()
          .user(prompt)
          .call()
          .content();
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Erro ao chamar LLM: " + e.getMessage());
    }
  }
}
