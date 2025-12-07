package br.com.viajaai.viajaai.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DayItineraryResponseDto {

  private String title;
  private List<String> activities;
}
