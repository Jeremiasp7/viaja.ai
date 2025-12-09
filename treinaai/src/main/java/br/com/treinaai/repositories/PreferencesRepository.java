package br.com.treinaai.repositories;

import br.com.planejaai.framework.repository.UserPreferencesRepository;
import br.com.treinaai.entities.PreferencesEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesRepository extends UserPreferencesRepository<PreferencesEntity> {}
