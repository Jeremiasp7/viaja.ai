package br.com.planejaai.framework.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import br.com.planejaai.framework.entity.UserEntity;
import br.com.planejaai.framework.exception.PreferenciasNaoEncontradasException;
import br.com.planejaai.framework.repository.UserPreferencesRepository;
import br.com.planejaai.framework.repository.UserRepository;

public abstract class UserPreferencesService<T extends UserPreferencesEntityAbstract> {

    protected final UserPreferencesRepository<T> repository;
    protected final UserRepository userRepository;

    protected UserPreferencesService(UserPreferencesRepository<T> repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public T buscarPreferenciasDoUsuario(UUID userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new PreferenciasNaoEncontradasException("Preferencias não encontrado com o id: " + userId));
    }

    @Transactional
    public T atualizarPreferencias(UUID userId, T incomingData) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new PreferenciasNaoEncontradasException("Usuario não encontrado com o id: " + userId));

        T existing = repository.findByUserId(userId).orElse(null);

        if (existing == null) {
            incomingData.setUser(user);
            return repository.save(incomingData);
        } else {
            updateProperties(existing, incomingData);
            
            existing.setUser(user);
            
            return repository.save(existing);
        }
    }

    protected abstract void updateProperties(T target, T source);
}