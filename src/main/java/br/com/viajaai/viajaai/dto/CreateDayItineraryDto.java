package br.com.viajaai.viajaai.dto;

import java.util.List;
import lombok.Data;

@Data
public class CreateDayItineraryDto {

  private String title;
  private List<String> activities;
}
