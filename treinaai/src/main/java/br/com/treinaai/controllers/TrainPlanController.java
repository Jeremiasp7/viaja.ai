package br.com.treinaai.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planejaai.framework.controller.GenericPlanController;
import br.com.treinaai.entities.TrainPlanEntity;
import br.com.treinaai.services.TrainPlanService;

@RestController
@RequestMapping("/api/plans")
public class TrainPlanController extends GenericPlanController<TrainPlanEntity>{

    public TrainPlanController(TrainPlanService service) {
		super(service);
	}

    @Override
    public TrainPlanEntity update(@PathVariable UUID userId, @RequestBody TrainPlanEntity plan) {
        return service.atualizarPlano(userId, plan);
    }
    
    @PostMapping("/update/{userId}")
    public TrainPlanEntity create(@PathVariable UUID userId, @RequestBody TrainPlanEntity plan) {
        return service.atualizarPlano(userId, plan);
    }

}
