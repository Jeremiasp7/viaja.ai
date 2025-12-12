package br.com.estudaai.entity;

import br.com.planejaai.framework.entity.BaseUserEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StudyUser extends BaseUserEntity {
  public String education;
}
