package br.com.viajaai.repositories;

import org.springframework.stereotype.Repository;

import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.viajaai.entities.TravelPlanEntity;

@Repository
public interface TravelPlanRepository extends GenericPlanRepository<TravelPlanEntity> {
    
}
