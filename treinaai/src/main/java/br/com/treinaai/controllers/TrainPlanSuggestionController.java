package br.com.treinaai.controllers;

import br.com.treinaai.dto.PlanSuggestionRequest;
import br.com.treinaai.strategy.TrainPlanSuggestionService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/suggestions")
public class TrainPlanSuggestionController {

  private final TrainPlanSuggestionService planSuggestionService;

  public TrainPlanSuggestionController(TrainPlanSuggestionService planSuggestionService) {
    this.planSuggestionService = planSuggestionService;
  }

  @PostMapping("/train-plan")
  public ResponseEntity<String> suggestTrainPlan(@RequestBody PlanSuggestionRequest request) {
    try {
      String suggestion =
          planSuggestionService.generatePlanWithPreferences(request.getUserId(), request.getPrompt());
      return ResponseEntity.ok(suggestion);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Erro ao gerar sugestão: " + e.getMessage() + " | " + e.getCause());
    }
  }

  @PostMapping("/train-plan/generic")
  public ResponseEntity<String> suggestTrainPlanFromGeneric(@RequestBody UUID planId) {
    try {
      String suggestion = planSuggestionService.generatePlanWithGenericPlan(planId);
      return ResponseEntity.ok(suggestion);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Erro ao gerar sugestão: " + e.getMessage());
    }
  }
}
