package br.com.treinaai.services;

import br.com.planejaai.framework.repository.BaseUserRepository;
import br.com.planejaai.framework.service.UserPreferencesService;
import br.com.treinaai.entities.PreferencesEntity;
import br.com.treinaai.repositories.PreferencesRepository;
import org.springframework.stereotype.Service;

@Service
public class PreferencesService extends UserPreferencesService<PreferencesEntity> {

  public PreferencesService(PreferencesRepository repository, BaseUserRepository userRepository) {
    super(repository, userRepository);
  }

  @Override
  protected void updateProperties(PreferencesEntity target, PreferencesEntity source) {
    target.setTipoDeTreino(source.getTipoDeTreino());
    target.setDuracaoTreino(source.getDuracaoTreino());
    target.setPreferenciaHorarios(source.getPreferenciaHorarios());
    target.setAtividadeRealizada(source.getAtividadeRealizada());
  }
}
