package br.com.viajaai.viajaai.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateTravelPlanDto {
  private String title;
  private LocalDate startDate;
  private LocalDate endDate;
  private UUID userId; // ID do usuário que está criando o plano
  private List<CreateDestinationDto> destinations;
  private List<CreateDayItineraryDto> dayItinerary;
}
