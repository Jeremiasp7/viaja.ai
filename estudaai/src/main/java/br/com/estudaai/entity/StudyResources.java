package br.com.estudaai.entity;

import br.com.planejaai.framework.entity.ResourcesEntityAbstract;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "study_resources")
public class StudyResources extends ResourcesEntityAbstract {
  @Column(nullable = false)
  private String materialName;

  private String authorOrInstructor;

  private String url;

  @Enumerated(EnumType.STRING)
  private MaterialType type;

  @Column(columnDefinition = "TEXT")
  private String description;
}
