package br.com.treinaai.repositories;

import org.springframework.stereotype.Repository;

import br.com.planejaai.framework.repository.UserPreferencesRepository;
import br.com.treinaai.entities.PreferencesEntity;

@Repository
public interface PreferencesRepository extends UserPreferencesRepository<PreferencesEntity> {
    
}
