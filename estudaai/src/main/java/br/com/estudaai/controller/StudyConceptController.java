package br.com.estudaai.controller;
import br.com.estudaai.service.ConceptService;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/concept")
@RestController
public class StudyConceptController {

    private final ConceptService conceptService;

    public StudyConceptController(ConceptService conceptService) {
        this.conceptService = conceptService;
    }

    @GetMapping("/explain")
    public ResponseEntity<String> explain(
            @RequestParam("query") String query,
            @RequestParam("userId") UUID userId) {
        
        String response = conceptService.recommendConcept(query, userId);
        return ResponseEntity.ok(response);
    }
}
