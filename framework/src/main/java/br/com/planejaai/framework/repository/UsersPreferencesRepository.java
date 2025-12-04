package br.com.planejaai.framework.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.planejaai.framework.entity.GenericUsersPreferencesEntity;

public interface UsersPreferencesRepository extends JpaRepository<GenericUsersPreferencesEntity, UUID>{

    Optional<GenericUsersPreferencesEntity> findByUserId(UUID userId);
} 
