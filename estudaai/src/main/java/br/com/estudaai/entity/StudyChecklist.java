package br.com.estudaai.entity;

import br.com.planejaai.framework.entity.ChecklistEntityAbstract;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "study_checklists")
public class StudyChecklist extends ChecklistEntityAbstract {
  @Column(name = "reference_location")
  private String reference;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StudyTaskType taskType;

  private Integer estimatedMinutes;
}
