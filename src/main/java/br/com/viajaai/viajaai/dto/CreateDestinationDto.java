package br.com.viajaai.viajaai.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CreateDestinationDto {
    private String country;
    private String city;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
}
