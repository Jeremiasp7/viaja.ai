package br.com.viajaai.viajaai.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DestinationResponseDto {
  private String country;
  private String city;
  private LocalDate arrivalDate;
  private LocalDate departureDate;
}
