package br.com.planejaai.framework.strategy;

import br.com.planejaai.framework.entity.BaseUserEntity;
import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import br.com.planejaai.framework.exception.PreferenciasNaoEncontradasException;
import br.com.planejaai.framework.exception.ResourceNotFoundException;
import br.com.planejaai.framework.repository.BaseUserRepository;
import br.com.planejaai.framework.repository.GenericPlanRepository;
import java.util.UUID;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class PlanSugestionService implements LlmStrategy<String> {

  protected final ChatClient chatClient;
  protected final BaseUserRepository userRepository;
  protected final GenericPlanRepository<? extends GenericPlanEntityAbstract> genericPlanRepository;

  public PlanSugestionService(
      ChatClient chatClient,
      BaseUserRepository userRepository,
      GenericPlanRepository<? extends GenericPlanEntityAbstract> genericPlanRepository) {
    this.chatClient = chatClient;
    this.userRepository = userRepository;
    this.genericPlanRepository = genericPlanRepository;
  }

  public String generatePlanWithPreferences(UUID userId, String prompt) {
    BaseUserEntity user =
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

    UserPreferencesEntityAbstract preferences = user.getPreferences();
    if (preferences == null) {
      throw new PreferenciasNaoEncontradasException("Usuário não possui preferências cadastradas.");
    }

    String effectivePrompt = buildPromptForPreferences(user, preferences, prompt);
    return chatClient.prompt().user(effectivePrompt).call().content();
  }

  public String generatePlanWithGenericPlan(UUID genericPlan) {
    GenericPlanEntityAbstract plan =
        genericPlanRepository
            .findById(genericPlan)
            .orElseThrow(() -> new ResourceNotFoundException("Plano não encontrado"));

    String resumoPlano =
        """
          O usuário possui um plano intitulado "%s".
          Ele planeja cumpri-lo de %s até %s.
        """
      .formatted(plan.getTitle(), plan.getStartDate(), plan.getEndDate());

    String prompt = buildPromptForGenericPlan(plan, resumoPlano);
    return chatClient.prompt().user(prompt).call().content();
  }

  @Override
  public String execute(String input) throws Exception {
    if (input == null || input.isBlank()) {
      throw new IllegalArgumentException("Input não pode ser vazio");
    }

    if (input.startsWith("GENERIC_PLAN:")) {
      String id = input.substring("GENERIC_PLAN:".length()).trim();
      UUID planId = UUID.fromString(id);
      return generatePlanWithGenericPlan(planId);
    }

    if (input.startsWith("USER:")) {
      String rest = input.substring("USER:".length());
      String[] splitted = rest.split("\\|", 2);
      if (splitted.length < 2) {
        throw new IllegalArgumentException("Formato inválido. Use 'USER:{uuid}|{prompt}'");
      }
      UUID userId = UUID.fromString(splitted[0].trim());
      String prompt = splitted[1].trim();
      return generatePlanWithPreferences(userId, prompt);
    }

    return chatClient.prompt().user(input).call().content();
  }

  protected String buildPromptForPreferences(
      BaseUserEntity user, UserPreferencesEntityAbstract preferences, String userPrompt) {
    StringBuilder sb = new StringBuilder();
    sb.append("Responda em português e de forma direta. ");
    sb.append("Preferências: ");
    sb.append("id=").append(preferences.getId()).append(". ");
    
    sb.append(userPrompt);
    return sb.toString();
  }

  protected String buildPromptForGenericPlan(GenericPlanEntityAbstract plan, String resumoPlano) {
    String prompt =
        """
                Responda em português e de forma direta, sem dar margem para continuar uma conversa.
                Haja como um especialista no assunto do conteúdo do roteiro e monte um roteiro detalhado
                com sugestões de atividades e demais coisas condizentes com o plano do usuário. Abaixo, terá o título e as datas do plano, além dos detalhes do plano do usuário.
                %s
                """
            .formatted(resumoPlano);
    return prompt;
  }
}
