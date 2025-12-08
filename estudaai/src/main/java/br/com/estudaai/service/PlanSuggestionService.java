package br.com.estudaai.service;

import org.springframework.ai.chat.client.ChatClient;

import br.com.planejaai.framework.strategy.PlanSugestionService;
import br.com.estudaai.repository.StudyPlanRepository;
import br.com.estudaai.repository.UserRepository;

public class PlanSuggestionService extends PlanSugestionService{


   public PlanSuggestionService(ChatClient.Builder chatClient, UserRepository userRepository, StudyPlanRepository studyPlanRepository){
     super(chatClient, userRepository, studyPlanRepository);
   }

}
	
