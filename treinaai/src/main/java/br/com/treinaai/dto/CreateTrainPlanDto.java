package br.com.treinaai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTrainPlanDto {

  @NotBlank(message = "Título é obrigatório")
  private String titulo;

  @NotBlank(message = "Descrição é obrigatória")
  private String descricao;

  @NotNull(message = "Data de início é obrigatória")
  private LocalDate dataInicio;

  @NotNull(message = "Data de término é obrigatória")
  private LocalDate dataTermino;

  @Positive(message = "Dias de treino deve ser maior que zero")
  private Integer diasTreino;

  @Positive(message = "Duração deve ser maior que zero")
  private Integer duracao;

  @NotEmpty(message = "Lista de atividades é obrigatória")
  private List<String> atividades;
}
