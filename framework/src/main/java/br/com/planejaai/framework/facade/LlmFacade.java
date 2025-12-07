package br.com.planejaai.framework.facade;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.planejaai.framework.strategy.PlanSugestionService;

@Component
public class LlmFacade {
    
    private final PlanSugestionService planSugestionService;

    public LlmFacade(PlanSugestionService planSugestionService) {
        this.planSugestionService = planSugestionService;
    }

    public String gerarRoteiroComPlano(UUID genericPlanId) throws Exception {
        return planSugestionService.generatePlanWithGenericPlan(genericPlanId);
    }
}
