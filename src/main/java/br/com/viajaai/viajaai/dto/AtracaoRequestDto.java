package br.com.viajaai.viajaai.dto;

import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtracaoRequestDto {
    private UUID userId;
    private String nome;
    private String cidade;
    private String pais;
}
