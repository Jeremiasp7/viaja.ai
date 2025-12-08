package br.com.viajaai.viajaai.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserPreferencesDto {

  private String estiloDeViagem;
  private String preferenciaDeAcomodacao;
  private String preferenciaDeClima;
  private String faixaOrcamentaria;
  private Integer duracaoDaViagem;
  private String companhiaDeViagem;
  private List<LocalDate> preferenciaDeDatas;
}
