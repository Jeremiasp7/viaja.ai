package br.com.viajaai.services;

import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.planejaai.framework.service.ChecklistService;
import br.com.viajaai.entities.ViagemChecklist;
import br.com.viajaai.repositories.ViagemChecklistRepository;
import org.springframework.stereotype.Service;

@Service
public class ViagemChecklistService extends ChecklistService<ViagemChecklist> {

  public ViagemChecklistService(
      ViagemChecklistRepository checklistRepository,
      GenericPlanRepository<?> planRepository) {
    super(checklistRepository, planRepository);
  }

  @Override
  protected void updateData(ViagemChecklist existing, ViagemChecklist incoming) {
    if (incoming.getCategoria() != null) {
      existing.setCategoria(incoming.getCategoria());
    }
    if (incoming.getQuantidade() != null) {
      existing.setQuantidade(incoming.getQuantidade());
    }
  }
}