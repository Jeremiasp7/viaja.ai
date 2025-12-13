package br.com.viajaai.repositories;

import br.com.viajaai.entities.ViagemBudget;
import br.com.planejaai.framework.repository.ResourcesRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ViagemBudgetRepository extends ResourcesRepository {

    ViagemBudget findByPlan_Id(UUID planId);
}