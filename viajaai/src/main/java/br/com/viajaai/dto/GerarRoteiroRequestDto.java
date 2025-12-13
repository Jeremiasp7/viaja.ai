package br.com.viajaai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GerarRoteiroRequestDto {
    private String destino;
    private Integer dias;
    private String estilo;
    private Double orcamento;
}