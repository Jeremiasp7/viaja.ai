package br.com.viajaai.viajaai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MustVisitPlaceDto {

  @JsonProperty("name")
  private String name;

  @JsonProperty("latitude")
  private double latitude;

  @JsonProperty("longitude")
  private double longitude;
}
