package br.com.viajaai.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViagemResponseDto {

  private UUID id;
  private String titulo;
  private String destino;
  private LocalDate dataInicio;
  private LocalDate dataFim;
  private String estiloViagem;
  
  private Double orcamentoTotal;
  private Double valorGasto;
  private Double saldoRestante;
  private String moeda;
}