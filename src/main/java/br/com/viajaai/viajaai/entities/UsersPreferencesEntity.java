package br.com.viajaai.viajaai.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users_preferences")
@Builder
public class UsersPreferencesEntity {

  // *algumas das variáveis abaixo podem ser transformadas em arrays

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  // perfil de viagem
  private String estiloDeViagem;
  private String preferenciaDeAcomodacao;
  private String preferenciaDeClima;

  // orçamento
  private String faixaOrcamentaria;
  private Integer duracaoDaViagem;

  // companhia
  private String companhiaDeViagem;
  private List<LocalDate> preferenciaDeDatas;

  @OneToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference // evitando erro de serialização infinita
  private UserEntity user;
}
