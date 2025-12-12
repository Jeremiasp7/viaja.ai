package br.com.treinaai.dto;

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
public class RecommendationRequest {
  @NotNull(message = "ID do plano é obrigatório")
  private UUID planId;

  private String context;

  @Positive(message = "Contagem deve ser maior que 0")
  @Builder.Default
  private Integer count = 5;
}
