package br.com.viajaai.viajaai.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateChecklistDto {
    private String nome;
    private boolean concluido;
    private String travelPlanId;
}
