package br.com.planejaai.framework.dto;

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
}
