package br.com.viajaai.services;

import br.com.planejaai.framework.strategy.ConceptDescriptionService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ViagemDicasService extends ConceptDescriptionService {

  public ViagemDicasService(ChatClient chatClient) {
    super(chatClient);
  }

  @Override
  public String generatePrompt(String query) {
    return """
        Você é um agente de viagens experiente e especialista em turismo internacional e nacional.
        O usuário tem a seguinte dúvida ou quer saber sobre o conceito: "%s".
        Explique de forma clara, objetiva e útil para um viajante, citando regras se necessário.
        """
        .formatted(query);
  }
}