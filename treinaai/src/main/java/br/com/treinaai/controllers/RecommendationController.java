package br.com.treinaai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.treinaai.dto.RecommendationRequest;
import br.com.treinaai.dto.RecommendationResponse;
import br.com.treinaai.services.RecommendationService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @PostMapping("/object")
    public ResponseEntity<RecommendationResponse> objectRecommendation(@RequestBody RecommendationRequest req) {
        return ResponseEntity.ok(new RecommendationResponse(service.recommendObjects(req.getPlanId(), req.getContext(), req.getCount())));
    }
}
