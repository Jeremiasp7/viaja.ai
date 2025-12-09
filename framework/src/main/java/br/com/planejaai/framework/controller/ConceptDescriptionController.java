package br.com.planejaai.framework.controller;

import br.com.planejaai.framework.strategy.ConceptDescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class ConceptDescriptionController {
  private final ConceptDescriptionService conceptService;

  public ConceptDescriptionController(ConceptDescriptionService conceptDescriptionService) {
    this.conceptService = conceptDescriptionService;
  }

  @GetMapping("/concept")
  public ResponseEntity<String> explain(@RequestParam("query") String query) {
    return ResponseEntity.ok(conceptService.recommendConcept(query));
  }
}
