package br.com.viajaai.viajaai.dto;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateBudgetDto {

  private Double totalAmount;
  private String currency;
  private List<String> categories;
  private UUID travelPlanId; // ID do plano de viagem a quem o orçamento se refere
  private UUID userId;
}
