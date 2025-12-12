package br.com.planejaai.framework.repository;

import br.com.planejaai.framework.entity.BaseUserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseUserRepository<T extends BaseUserEntity> extends JpaRepository<T, UUID> {
  Optional<T> findByEmail(String email);
}
