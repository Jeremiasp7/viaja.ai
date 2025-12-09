package br.com.treinaai.entities;

import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import jakarta.persistence.Entity;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesEntity extends UserPreferencesEntityAbstract {

  private String tipoDeTreino;
  private Integer duracaoTreino;
  private List<LocalTime> preferenciaHorarios;
  private String atividadeRealizada;
}
