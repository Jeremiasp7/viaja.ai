package br.com.treinaai.services;

import org.springframework.stereotype.Service;

import br.com.planejaai.framework.repository.BaseUserRepository;
import br.com.planejaai.framework.service.GenericPlanService;
import br.com.treinaai.entities.TrainPlanEntity;
import br.com.treinaai.repositories.TrainPlanRepository;

@Service
public class TrainPlanService extends GenericPlanService<TrainPlanEntity>{
    
    public TrainPlanService(TrainPlanRepository repository, BaseUserRepository userRepository) {
		super(repository, userRepository);
	}

    @Override
    protected void updateProperties(TrainPlanEntity target, TrainPlanEntity source) {
		target.setDayActivities(source.getDayActivities());
	}
}
