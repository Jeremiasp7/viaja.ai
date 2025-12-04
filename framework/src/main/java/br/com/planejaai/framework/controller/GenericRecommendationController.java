package br.com.planejaai.framework.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import br.com.planejaai.framework.dto.GenericRecommendationRequestDto;
import br.com.planejaai.framework.dto.GenericRecommendedDestinationDto;
import br.com.planejaai.framework.service.interfaces.DestinationRecommendationStrategy;

@RestController
@RequestMapping("/recomendacoes")
public class GenericRecommendationController {

    private final DestinationRecommendationStrategy strategy;


    public GenericRecommendationController(DestinationRecommendationStrategy strategy) {
        this.strategy = strategy;
    }

    @PostMapping("/{userId}")
    public List<GenericRecommendedDestinationDto> recommendForUser(
            @PathVariable UUID userId,
            @RequestBody GenericRecommendationRequestDto request
    ) {
        return strategy.recommendForUser(userId, request);
    }
}
