package br.com.treinaai.entities;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrainPlanEntity extends GenericPlanEntityAbstract {

  private String descricao;
  private Integer diasTreino;
  private Integer duracao;
  private List<String> dayActivities;
}
