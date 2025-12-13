package br.com.viajaai.services;

import br.com.planejaai.framework.exception.ResourceNotFoundException;
import br.com.planejaai.framework.service.ResourcesService;
import br.com.viajaai.entities.ViagemBudget;
import br.com.viajaai.repositories.ViagemBudgetRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ViagemBudgetService extends ResourcesService<ViagemBudget> {

  public ViagemBudgetService(ViagemBudgetRepository resourcesRepository) {
    super(resourcesRepository);
  }

  @Override
  @Transactional
  public ViagemBudget update(UUID id, ViagemBudget updated) {

    ViagemBudget existing = super.update(id, updated);

    if (updated.getValorGasto() != null) {
      existing.setValorGasto(updated.getValorGasto());
    }

    return resourcesRepository.save(existing);
  }

  @Override
  public ViagemBudget create(ViagemBudget resource) {
    return this.save(resource);
  }
}