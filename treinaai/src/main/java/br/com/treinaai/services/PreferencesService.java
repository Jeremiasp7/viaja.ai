package br.com.treinaai.services;

import org.springframework.stereotype.Service;

import br.com.planejaai.framework.repository.UserRepository;
import br.com.planejaai.framework.service.UserPreferencesService;
import br.com.treinaai.entities.PreferencesEntity;
import br.com.treinaai.repositories.PreferencesRepository;

@Service
public class PreferencesService extends UserPreferencesService<PreferencesEntity> {

	public PreferencesService(PreferencesRepository repository, UserRepository userRepository) {
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
