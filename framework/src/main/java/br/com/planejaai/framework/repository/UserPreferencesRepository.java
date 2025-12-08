package br.com.planejaai.framework.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;

@NoRepositoryBean
public interface UserPreferencesRepository<T extends UserPreferencesEntityAbstract> extends JpaRepository<T, UUID> {

    Optional<T> findByUserId(UUID userId);
}