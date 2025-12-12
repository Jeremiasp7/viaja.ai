package br.com.treinaai.strategy;

import br.com.planejaai.framework.strategy.ConceptDescriptionService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TrainConceptDescriptionService extends ConceptDescriptionService {

  public TrainConceptDescriptionService(ChatClient chatClient) {
    super(chatClient);
  }

  @Override
  public String generatePrompt(String query) {
    return """
        Você é um especialista em treinamento e exercícios físicos.
        Responda em português de forma concisa e prática.
        
        Pergunta do usuário: %s
        
        Forneça uma explicação clara sobre o conceito, incluindo:
        1. Definição sucinta
        2. Importância para o treinamento
        3. Dicas práticas de aplicação
        """.formatted(query);
  }
}
