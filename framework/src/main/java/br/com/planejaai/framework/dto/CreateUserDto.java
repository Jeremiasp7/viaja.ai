package br.com.planejaai.framework.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
  @Email(message = "Email inválido")
  @NotBlank(message = "Email não pode estar vazio")
  private String email;

  @NotBlank(message = "Nome não pode estar vazio")
  @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
  private String nome;

  @NotBlank(message = "Senha não pode estar vazia")
  @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
  private String senha;
}
