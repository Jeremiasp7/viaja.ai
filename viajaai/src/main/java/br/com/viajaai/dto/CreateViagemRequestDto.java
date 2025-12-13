package br.com.viajaai.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateViagemRequestDto {

  @NotBlank(message = "O título da viagem é obrigatório")
  private String titulo;

  @NotBlank(message = "O destino é obrigatório")
  private String destino;

  @NotNull(message = "A data de início é obrigatória")
  @FutureOrPresent(message = "A data de início não pode ser no passado")
  private LocalDate dataInicio;

  @NotNull(message = "A data de fim é obrigatória")
  @Future(message = "A data de fim deve ser futura")
  private LocalDate dataFim;

  private String estiloViagem;

  @NotNull(message = "O orçamento total é obrigatório")
  @Min(value = 0, message = "O orçamento deve ser positivo")
  private Double orcamentoTotal;

}