package br.com.viajaai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViagemPreferencesDto {
    private String assentoPreferencia;
    private String restricoesAlimentares;
    private boolean aceitaEscalas;
}