package br.com.viajaai.services;

import org.springframework.stereotype.Service;

import br.com.planejaai.framework.repository.UserRepository;
import br.com.planejaai.framework.service.UserPreferencesService;
import br.com.viajaai.entities.PreferencesEntity;
import br.com.viajaai.repositories.PreferencesRepository;

@Service
public class PreferencesService extends UserPreferencesService<PreferencesEntity> {

    public PreferencesService(PreferencesRepository repository, UserRepository userRepository) {
        super(repository, userRepository);
    }

    @Override
    protected void updateProperties(PreferencesEntity target, PreferencesEntity source) {
        target.setEstiloDeViagem(source.getEstiloDeViagem());
        target.setPreferenciaDeAcomodacao(source.getPreferenciaDeAcomodacao());
        target.setPreferenciaDeClima(source.getPreferenciaDeClima());

        target.setFaixaOrcamentaria(source.getFaixaOrcamentaria());
        target.setDuracaoDaViagem(source.getDuracaoDaViagem());

        target.setCompanhiaDeViagem(source.getCompanhiaDeViagem());
        target.setPreferenciaDeDatas(source.getPreferenciaDeDatas());
    }
}