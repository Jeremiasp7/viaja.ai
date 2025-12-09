package br.com.treinaai.repositories;

import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.treinaai.entities.TrainPlanEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainPlanRepository extends GenericPlanRepository<TrainPlanEntity> {}
