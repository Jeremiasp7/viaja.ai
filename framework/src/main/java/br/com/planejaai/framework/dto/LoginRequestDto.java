package br.com.planejaai.framework.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
  @Email(message = "Email inválido")
  @NotBlank(message = "Email não pode estar vazio")
  private String email;

  @NotBlank(message = "Senha não pode estar vazia")
  private String senha;
}
