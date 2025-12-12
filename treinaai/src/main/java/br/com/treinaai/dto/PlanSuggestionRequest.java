package br.com.treinaai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanSuggestionRequest {
  @NotNull(message = "ID do usuário é obrigatório")
  private UUID userId;

  @Positive(message = "Número de dias deve ser maior que 0")
  @Builder.Default
  private Integer days = 7;

  private String context;

  private UUID genericPlanId;

  @NotBlank(message = "Prompt não pode estar vazio")
  private String prompt;
}
