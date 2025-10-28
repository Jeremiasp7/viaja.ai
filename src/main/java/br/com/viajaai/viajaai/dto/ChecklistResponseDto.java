package br.com.viajaai.viajaai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistResponseDto {
    private UUID id;
    private String nome;
    private boolean concluido;
    private UUID travelPlanId;
}
