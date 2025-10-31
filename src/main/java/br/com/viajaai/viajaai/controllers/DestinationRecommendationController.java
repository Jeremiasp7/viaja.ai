package br.com.viajaai.viajaai.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.viajaai.viajaai.dto.RecommendDestinationRequestDto;
import br.com.viajaai.viajaai.dto.RecommendedDestinationDto;
import br.com.viajaai.viajaai.services.DestinationRecommendationService;

@RestController
@RequestMapping("/recomendacoes")
public class DestinationRecommendationController {

    private final DestinationRecommendationService service;

    @Autowired
    public DestinationRecommendationController(DestinationRecommendationService service) {
        this.service = service;
    }

    @PostMapping("/{userId}")
    public List<RecommendedDestinationDto> recommendForUser(@PathVariable UUID userId, @RequestBody RecommendDestinationRequestDto recommendDestinationRequestDto) {
        return service.recommendForUser(userId, recommendDestinationRequestDto);
    }
}
