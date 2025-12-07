package br.com.planejaai.framework.strategy;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.planejaai.framework.repository.UserRepository;
import java.util.UUID;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class PlanSugestionService implements LlmStrategy {

  private final ChatClient chatClient;
  private final UserRepository userRepository;
  private final GenericPlanRepository genericPlanRepository;

  public PlanSugestionService(
      ChatClient.Builder chatClientBuilder,
      UserRepository userRepository,
      GenericPlanRepository genericPlanRepository) {
    this.chatClient = chatClientBuilder.build();
    this.userRepository = userRepository;
    this.genericPlanRepository = genericPlanRepository;
  }

  // public String generatePlanWithPreferences(UUID userId) {
  /** faltam as preferências */
  // }

  public String generatePlanWithGenericPlan(UUID genericPlan) throws Exception {
    GenericPlanEntityAbstract plan =
        genericPlanRepository
            .findById(genericPlan)
            .orElseThrow(() -> new Exception("Plano de viagem não encontrado"));

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
