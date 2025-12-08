package br.com.planejaai.framework.strategy;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import br.com.planejaai.framework.entity.UserEntity;
import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.planejaai.framework.repository.UserRepository;
import java.util.UUID;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class PlanSugestionService implements LlmStrategy {

  protected final ChatClient chatClient;
  protected final UserRepository userRepository;
  protected final GenericPlanRepository<? extends GenericPlanEntityAbstract> genericPlanRepository;

  public PlanSugestionService(
      ChatClient.Builder chatClientBuilder,
      UserRepository userRepository,
      GenericPlanRepository<? extends GenericPlanEntityAbstract> genericPlanRepository) {
    this.chatClient = chatClientBuilder.build();
    this.userRepository = userRepository;
    this.genericPlanRepository = genericPlanRepository;
  }

  public String generatePlanWithPreferences(UUID userId, String prompt) throws Exception { 
    UserEntity user = userRepository
      .findById(userId)
        .orElseThrow(() -> new Exception("Usuário não encontrado"));
    
    UserPreferencesEntityAbstract preferences = user.getPreferences();
    if (preferences == null) {
      throw new Exception("Usuário não possui preferências cadastradas.");
    }

    return chatClient.prompt().user(prompt).call().content();
  }

  public String generatePlanWithGenericPlan(UUID genericPlan) throws Exception {
    GenericPlanEntityAbstract plan =
      genericPlanRepository.findById(genericPlan)
        .orElseThrow(() -> new Exception("Plano não encontrado"));

    String resumoPlano =
        """
                O usuário possui um plano intitulado "%s".
                Ele planeja cumpri-lo de %s até %s.
                """
            .formatted(plan.getTitle(), plan.getStartDate(), plan.getEndDate());

    String prompt =
        """
                Responda em português e de forma direta, sem dar margem para continuar uma conversa.
                Haja como um especialista no assunto do conteúdo do roteiro e monte um roteiro detalhado
                com sugestões de atividades e demais coisas condizentes com o plano do usuário. Abaixo, terá o título e as datas do plano, além dos detalhes do plano do usuário.
                %s
                """
            .formatted(resumoPlano);

    // a única coisa que o usuário deverá acrescentar são os detalhes do seu plano

    return chatClient.prompt().user(prompt).call().content();
  }

  @Override
  public Object execute(String input) {
    throw new UnsupportedOperationException("Use os métodos específicos");
  }
}
