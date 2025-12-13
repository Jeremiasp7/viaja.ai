package br.com.viajaai.services;

import br.com.planejaai.framework.repository.BaseUserRepository;
import br.com.planejaai.framework.service.GenericPlanService;
import br.com.viajaai.entities.ViagemPlan;
import br.com.viajaai.repositories.ViagemPlanRepository;
import org.springframework.stereotype.Service;

@Service
public class ViagemPlanService extends GenericPlanService<ViagemPlan> {

  public ViagemPlanService(
      ViagemPlanRepository repository, 
      BaseUserRepository userRepository) {
    super(repository, userRepository);
  }

  @Override
  protected void updateProperties(ViagemPlan target, ViagemPlan source) {
    if (source.getTitle() != null) {
      target.setTitle(source.getTitle());
    }
    if (source.getStartDate() != null) {
      target.setStartDate(source.getStartDate());
    }
    if (source.getEndDate() != null) {
      target.setEndDate(source.getEndDate());
    }

    if (source.getDestino() != null) {
      target.setDestino(source.getDestino());
    }
    if (source.getEstiloViagem() != null) {
      target.setEstiloViagem(source.getEstiloViagem());
    }
    if (source.getNumeroPessoas() != null) {
      target.setNumeroPessoas(source.getNumeroPessoas());
    }
  }
}