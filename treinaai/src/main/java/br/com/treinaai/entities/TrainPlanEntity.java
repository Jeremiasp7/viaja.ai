package br.com.treinaai.entities;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
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
@Builder
@Entity
@Table(name = "train_plans")
public class TrainPlanEntity extends GenericPlanEntityAbstract {

  @Column(nullable = false)
  private String descricao;

  @Column(nullable = false)
  private Integer diasTreino;

  @Column(nullable = false)
  private Integer duracao;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "train_plan_activities",
      joinColumns = @JoinColumn(name = "train_plan_id"))
  @Column(name = "activity")
  private List<String> dayActivities;
}
