package br.com.treinaai.entities;

import br.com.planejaai.framework.entity.ResourcesEntityAbstract;
import jakarta.persistence.Entity;
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
public class TrainResourcesEntity extends ResourcesEntityAbstract {

  private String thoughts;
}
