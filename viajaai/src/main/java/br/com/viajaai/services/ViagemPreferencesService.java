package br.com.viajaai.services;

import br.com.planejaai.framework.repository.BaseUserRepository;
import br.com.planejaai.framework.service.UserPreferencesService;
import br.com.viajaai.entities.ViagemPreferences;
import br.com.viajaai.repositories.ViagemPreferencesRepository;
import org.springframework.stereotype.Service;

@Service
public class ViagemPreferencesService extends UserPreferencesService<ViagemPreferences> {

  public ViagemPreferencesService(
      ViagemPreferencesRepository repository, 
      BaseUserRepository userRepository) {
    super(repository, userRepository);
  }

  @Override
  protected void updateProperties(ViagemPreferences target, ViagemPreferences source) {

    if (source.getAssentoPreferencia() != null) {
      target.setAssentoPreferencia(source.getAssentoPreferencia());
    }
    if (source.getRestricoesAlimentares() != null) {
      target.setRestricoesAlimentares(source.getRestricoesAlimentares());
    }

    target.setAceitaEscalas(source.isAceitaEscalas());
  }
}