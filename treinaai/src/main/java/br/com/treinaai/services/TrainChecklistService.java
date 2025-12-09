package br.com.treinaai.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.planejaai.framework.service.ChecklistService;
import br.com.treinaai.entities.TrainChecklistEntity;
import br.com.treinaai.repositories.TrainChecklistRepository;

@Service
public class TrainChecklistService extends ChecklistService<TrainChecklistEntity> {

    public TrainChecklistService(TrainChecklistRepository checklistRepository,
                                 GenericPlanRepository<? extends GenericPlanEntityAbstract> planRepository) {
        super(checklistRepository, planRepository);
    }

    @Override
    protected void updateData(TrainChecklistEntity existing, TrainChecklistEntity incoming) {
        // TreinaAI-specific update logic (currently no extra fields besides base)
        if (incoming.getNotes() != null) existing.setNotes(incoming.getNotes());
    }

    @Override
    public TrainChecklistEntity create(TrainChecklistEntity entity) {
        return super.create(entity);
    }

    @Override
    public java.util.List<TrainChecklistEntity> findByPlanId(java.util.UUID planId) {
        return super.findByPlanId(planId);
    }

    @Override
    public TrainChecklistEntity findById(java.util.UUID id) {
        return super.findById(id);
    }

    @Override
    public TrainChecklistEntity update(java.util.UUID id, TrainChecklistEntity incoming) {
        return super.update(id, incoming);
    }

    @Override
    public void delete(java.util.UUID id) {
        super.delete(id);
    }

}
