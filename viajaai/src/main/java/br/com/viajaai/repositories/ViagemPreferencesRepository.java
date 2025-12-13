package br.com.viajaai.repositories;
import br.com.viajaai.entities.ViagemPreferences;
import br.com.planejaai.framework.repository.UserPreferencesRepository; 

import org.springframework.stereotype.Repository;

@Repository
public interface ViagemPreferencesRepository extends UserPreferencesRepository<ViagemPreferences> {
}