package br.com.estudaai.entity;

import br.com.planejaai.framework.entity.BaseUserEntity;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
public class StudyUser extends BaseUserEntity{
  public String education;
}
