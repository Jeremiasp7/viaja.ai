package br.com.viajaai.viajaai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class RecommendedDestinationDto {

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("mustVisitPlaces")
    private List<MustVisitPlaceDto> mustVisitPlaces;
}
