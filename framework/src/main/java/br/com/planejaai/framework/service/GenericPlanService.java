package br.com.planejaai.framework.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import br.com.planejaai.framework.entity.UserEntity;
import br.com.planejaai.framework.exception.PreferenciasNaoEncontradasException;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.planejaai.framework.repository.UserRepository;
import jakarta.transaction.Transactional;

public abstract class GenericPlanService <T extends GenericPlanEntityAbstract> {
    
    protected final GenericPlanRepository<T> repository;
    protected final UserRepository userRepository;

    protected GenericPlanService(GenericPlanRepository<T> repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public T atualizarPlano(UUID userId, T incomingData) {
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

    public List<GenericPlanEntityAbstract> listarPlanos(UUID userId) throws UsuarioNaoEncontradoException {
        if (!userRepository.existsById(userId)) {
        throw new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + userId);
        }

        return repository.findByUserId(userId).stream()
            .collect(Collectors.toList());
    }

    public void deleteTravelPlan(UUID id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("Plano de viagem não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

}
