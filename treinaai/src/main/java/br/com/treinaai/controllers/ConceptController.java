package br.com.treinaai.controllers;

import br.com.treinaai.strategy.TrainConceptDescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/concepts")
public class ConceptController {

  private final TrainConceptDescriptionService conceptService;

  public ConceptController(TrainConceptDescriptionService conceptService) {
    this.conceptService = conceptService;
  }

  @GetMapping("/explain")
  public ResponseEntity<String> explain(@RequestParam String query) {
    try {
      String explanation = conceptService.recommendConcept(query);
      return ResponseEntity.ok(explanation);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Erro ao gerar explicação: " + e.getMessage());
    }
  }
}
