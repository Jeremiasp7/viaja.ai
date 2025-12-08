package br.com.planejaai.framework.repository;

import br.com.planejaai.framework.entity.BaseUserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface BaseUserRepository extends JpaRepository<BaseUserEntity, UUID> {
  Optional<BaseUserEntity> findByEmail(String email);
}
