package br.com.viajaai.repositories;

import br.com.viajaai.entities.ViagemChecklist;

import br.com.planejaai.framework.repository.ChecklistRepository;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ViagemChecklistRepository extends ChecklistRepository<ViagemChecklist> {
    
    List<ViagemChecklist> findByPlanIdAndCategoria(UUID planId, String categoria);
}