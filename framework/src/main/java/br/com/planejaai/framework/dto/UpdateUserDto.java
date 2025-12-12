package br.com.planejaai.framework.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDto {

  @Email(message = "Email inválido")
  private String email;

  @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
  private String nome;

  @Size(min = 6, message = "Nova senha deve ter pelo menos 6 caracteres")
  private String novaSenha;

  private String senhaAntiga;
}
