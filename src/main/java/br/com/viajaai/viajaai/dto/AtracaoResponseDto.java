package br.com.viajaai.viajaai.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtracaoResponseDto {
    private String nome;
    private String cidade;
    private String pais;
    private String descricao;

    @JsonProperty("checklist_sugerido")
    private List<String> checklistSugerido;
}
