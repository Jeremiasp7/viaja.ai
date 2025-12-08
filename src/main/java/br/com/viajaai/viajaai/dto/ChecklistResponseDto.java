package br.com.viajaai.viajaai.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistResponseDto {
  private UUID id;
  private String nome;
  private boolean concluido;
  private UUID travelPlanId;
}
