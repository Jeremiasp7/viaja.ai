package br.com.viajaai.viajaai.repositories;

import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersPreferencesRepository extends JpaRepository<UsersPreferencesEntity, UUID> {

  Optional<UsersPreferencesEntity> findByUserId(UUID userId);
}
