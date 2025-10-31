package br.com.viajaai.viajaai.dto;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TravelPlanResponseDto {
    private UUID id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DestinationResponseDto> destinations;
    private List<DayItineraryResponseDto> dayItinerary;
}