package br.com.viajaai.viajaai.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreferencesUserDto {

    private String estiloDeViagem;
    private String preferenciaDeAcomodacao;  
    private String preferenciaDeClima; 
    private String faixaOrcamentaria;
    private Integer duracaoDaViagem;
    private String companhiaDeViagem;
    private List<LocalDate> preferenciaDeDatas;
    private UUID userId;
}
