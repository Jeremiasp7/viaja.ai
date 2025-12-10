package br.com.treinaai.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.treinaai.dto.PlanSuggestionRequest;
import br.com.planejaai.framework.facade.LlmFacade;

@RestController
@RequestMapping("/api/suggestions")
public class PlanSuggestionController {

    private final LlmFacade llmFacade;

    public PlanSuggestionController(LlmFacade llmFacade) {
        this.llmFacade = llmFacade;
    }

    @PostMapping("/plan")
    public ResponseEntity<String> suggestPlan(@RequestBody PlanSuggestionRequest req) {
        try {
            if (req.getGenericPlanId() != null) {
                return ResponseEntity.ok(llmFacade.gerarRoteiroComPlano(req.getGenericPlanId()));
            }
            return ResponseEntity.ok(llmFacade.gerarRoteiroComPreferencias(req.getUserId(), req.getPrompt() != null ? req.getPrompt() : req.getContext()));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Falha ao gerar sugestão: " + ex.getMessage());
        }
    }
}
