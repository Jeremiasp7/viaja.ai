package br.com.treinaai.repositories;

import org.springframework.stereotype.Repository;

import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.treinaai.entities.TrainPlanEntity;

@Repository
public interface TrainPlanRepository extends GenericPlanRepository<TrainPlanEntity> {
    
}
