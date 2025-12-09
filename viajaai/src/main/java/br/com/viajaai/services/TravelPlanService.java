package br.com.viajaai.services;

import org.springframework.stereotype.Service;

import br.com.planejaai.framework.repository.UserRepository;
import br.com.planejaai.framework.service.GenericPlanService;
import br.com.viajaai.entities.TravelPlanEntity;
import br.com.viajaai.repositories.TravelPlanRepository;

@Service
public class TravelPlanService extends GenericPlanService<TravelPlanEntity> {
    
    public TravelPlanService(TravelPlanRepository repository, UserRepository userRepository) {
        super(repository, userRepository);
    }

    @Override
    protected void updateProperties(TravelPlanEntity target, TravelPlanEntity source) {
        target.setTitle(source.getTitle());
        target.setStartDate(source.getStartDate());
        target.setEndDate(source.getEndDate());

        target.setDestinations(source.getDestinations());
        target.setDayItinerary(source.getDayItinerary());
        target.setBudget(source.getBudget());
    }
}