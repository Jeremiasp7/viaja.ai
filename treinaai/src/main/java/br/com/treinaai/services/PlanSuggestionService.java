package br.com.treinaai.services;

import br.com.planejaai.framework.strategy.PlanSugestionService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;

import br.com.planejaai.framework.entity.BaseUserEntity;
import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.planejaai.framework.repository.BaseUserRepository;

@Service
@Primary
public class PlanSuggestionService extends PlanSugestionService {

  public PlanSuggestionService(
      ChatClient.Builder chatClientBuilder,
      BaseUserRepository userRepository,
      GenericPlanRepository<? extends br.com.planejaai.framework.entity.GenericPlanEntityAbstract> genericPlanRepository) {
    super(chatClientBuilder, userRepository, genericPlanRepository);
  }

  @Override
  protected String buildPromptForPreferences(BaseUserEntity user, UserPreferencesEntityAbstract preferences, String userPrompt) {
    // TreinaAI-specific prompt: include user name and preference summary
    String prefSummary = "";
    if (preferences != null) {
      prefSummary = "Tipo de treino: " + (preferences.getClass().getSimpleName()) + "; use isto como contexto. ";
    }
    return "Responda em português de forma direta. Usuário: " + user.getNome() + ". " + prefSummary + userPrompt;
  }

  @Override
  protected String buildPromptForGenericPlan(
      br.com.planejaai.framework.entity.GenericPlanEntityAbstract plan, String resumoPlano) {
    // Keep framework default but add TreinaAI context
    String base = super.buildPromptForGenericPlan(plan, resumoPlano);
    return base + "\nUtilize esse plano como base para criar um plano de treinos detalhado.";
  }
}
