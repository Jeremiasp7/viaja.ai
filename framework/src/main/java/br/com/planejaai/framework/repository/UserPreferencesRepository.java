package br.com.planejaai.framework.repository;

import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserPreferencesRepository<T extends UserPreferencesEntityAbstract>
    extends JpaRepository<T, UUID> {

  Optional<T> findByUserId(UUID userId);
}
