package br.com.viajaai.viajaai.dto;

import java.util.List;
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
    private List<String> checklistSugerido;
}
