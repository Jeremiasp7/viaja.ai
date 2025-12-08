package br.com.planejaai.framework.service;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import br.com.planejaai.framework.entity.ChecklistEntityAbstract;
import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import br.com.planejaai.framework.exception.PreferenciasNaoEncontradasException;
import br.com.planejaai.framework.repository.ChecklistRepository;
import br.com.planejaai.framework.repository.GenericPlanRepository;

public abstract class ChecklistService<T extends ChecklistEntityAbstract> {

    protected final ChecklistRepository<T> checklistRepository;

    protected final GenericPlanRepository<? extends GenericPlanEntityAbstract> planRepository;

    protected ChecklistService(ChecklistRepository<T> checklistRepository, 
                               GenericPlanRepository<? extends GenericPlanEntityAbstract> planRepository) {
        this.checklistRepository = checklistRepository;
        this.planRepository = planRepository;
    }


    @Transactional
    public T create(T entity) {
        if (entity.getPlan() != null && entity.getPlan().getId() != null) {
            boolean planExists = planRepository.existsById(entity.getPlan().getId());
            if (!planExists) {
                throw new PreferenciasNaoEncontradasException("Plano não encontrado com o id: " + entity.getPlan().getId());
            }
        }
        return checklistRepository.save(entity);
    }

    public List<T> findByPlanId(UUID planId) {
        if (!planRepository.existsById(planId)) {
            throw new PreferenciasNaoEncontradasException("Plano não encontrado com o id: " + planId);
        }
        return checklistRepository.findByPlanId(planId);
    }

    public T findById(UUID id) {
        return checklistRepository.findById(id)
                .orElseThrow(() -> new PreferenciasNaoEncontradasException("Checklist não encontrado com o id: " + id));
    }

    @Transactional
    public T update(UUID id, T incoming) {
        T existing = findById(id);

        if (incoming.getName() != null) existing.setName(incoming.getName());
        if (incoming.getCompleted() != null) existing.setCompleted(incoming.getCompleted());

        updateData(existing, incoming);

        return checklistRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!checklistRepository.existsById(id)) {
            throw new PreferenciasNaoEncontradasException("Checklist não encontrado com o id: " + id);
        }
        checklistRepository.deleteById(id);
    }

    protected abstract void updateData(T existing, T incoming);
}