package br.com.viajaai.repositories;

import org.springframework.stereotype.Repository;

import br.com.planejaai.framework.repository.UserPreferencesRepository;
import br.com.viajaai.entities.PreferencesEntity;

@Repository
public interface PreferencesRepository extends UserPreferencesRepository<PreferencesEntity> {
    
}
