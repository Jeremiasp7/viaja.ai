package br.com.treinaai.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainPlanResponseDto {
  private UUID id;
  private String titulo;
  private LocalDate dataInicio;
  private LocalDate dataTermino;
  private String descricao;
  private Integer diasTreino;
  private Integer duracao;
  private List<String> atividades;
}
