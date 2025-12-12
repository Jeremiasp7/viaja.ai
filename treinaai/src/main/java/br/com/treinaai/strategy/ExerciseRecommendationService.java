package br.com.treinaai.strategy;

import br.com.planejaai.framework.strategy.ObjectRecommendationService;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class ExerciseRecommendationService extends ObjectRecommendationService<String> {

  public ExerciseRecommendationService(ChatClient chatClient) {
    super(chatClient);
  }

  public List<String> suggestExercises(String muscleGroup, String difficulty) {
    String prompt =
        """
        Você é um especialista em exercícios físicos. Recomende 5 exercícios para %s com nível de dificuldade %s.
        
        Forneça apenas uma lista com os nomes dos exercícios, um por linha.
        Cada exercício deve incluir: nome, série/repetições recomendadas.
        """
            .formatted(muscleGroup, difficulty);

    return execute(prompt);
  }

  @Override
  protected List<String> executeRecommendation(String prompt, ParameterizedTypeReference<List<String>> responseType) {
    return super.executeRecommendation(prompt, responseType);
  }
}
