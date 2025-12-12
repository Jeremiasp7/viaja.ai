package br.com.treinaai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

  @Positive(message = "Dias de treino deve ser maior que zero")
  private Integer diasTreino;

  @Positive(message = "Duração deve ser maior que zero")
  private Integer duracao;
}
