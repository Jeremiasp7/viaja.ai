package br.com.planejaai.framework.facade;

import br.com.planejaai.framework.strategy.PlanSugestionService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class LlmFacade {

  private final PlanSugestionService planSugestionService;

  public LlmFacade(PlanSugestionService planSugestionService) {
    this.planSugestionService = planSugestionService;
  }

  public String gerarRoteiroComPlano(UUID genericPlanId) throws Exception {
    return planSugestionService.generatePlanWithGenericPlan(genericPlanId);
  }

  public String gerarRoteiroComPreferencias(UUID userId, String prompt) throws Exception {
    return planSugestionService.generatePlanWithPreferences(userId, prompt);
  }
}
