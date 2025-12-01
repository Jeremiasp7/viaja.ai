package br.com.viajaai.viajaai.dto;

import br.com.viajaai.viajaai.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDto {
    
    private String email;
    private String nome;
    private String novaSenha;
    private String senhaAntiga;
    private Role role;
}
