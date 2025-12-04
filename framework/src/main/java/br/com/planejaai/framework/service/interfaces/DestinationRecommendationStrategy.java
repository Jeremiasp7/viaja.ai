package br.com.planejaai.framework.service.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.planejaai.framework.dto.GenericRecommendationRequestDto;
import br.com.planejaai.framework.dto.GenericRecommendedDestinationDto;


public interface DestinationRecommendationStrategy {

    List<GenericRecommendedDestinationDto> recommendForUser(UUID userId, GenericRecommendationRequestDto request);
}
