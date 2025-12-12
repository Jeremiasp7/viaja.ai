package br.com.estudaai.entity;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "study_plans")
public class StudyPlanEntity extends GenericPlanEntityAbstract {

  @Column(nullable = false)
  private String subjectArea;

  private String objective;

  private Integer targetHours;
  private Integer completedHours;
}
