package br.com.estudaai.service;

import br.com.estudaai.repository.StudyPlanRepository;
import br.com.estudaai.repository.UserRepository;
import br.com.planejaai.framework.strategy.PlanSugestionService;
import org.springframework.ai.chat.client.ChatClient;

public class PlanSuggestionService extends PlanSugestionService {

  public PlanSuggestionService(
      ChatClient.Builder chatClient,
      UserRepository userRepository,
      StudyPlanRepository studyPlanRepository) {
    super(chatClient, userRepository, studyPlanRepository);
  }
}
