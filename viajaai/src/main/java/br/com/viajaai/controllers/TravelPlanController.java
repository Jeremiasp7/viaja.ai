package br.com.viajaai.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planejaai.framework.controller.GenericPlanController;
import br.com.viajaai.entities.TravelPlanEntity;
import br.com.viajaai.services.TravelPlanService;

@RestController
@RequestMapping("/api/travel-plans")
public class TravelPlanController extends GenericPlanController<TravelPlanEntity> {

    private final TravelPlanService travelService;

    public TravelPlanController(TravelPlanService service) {
        super(service);
        this.travelService = service;
    }

    @Override
    @PutMapping("/{userId}")
    public TravelPlanEntity update(@PathVariable UUID userId, @RequestBody TravelPlanEntity plan) {
        return travelService.atualizarPlano(userId, plan);
    }
    
    @PostMapping("/{userId}")
    public TravelPlanEntity create(@PathVariable UUID userId, @RequestBody TravelPlanEntity plan) {
        return travelService.atualizarPlano(userId, plan);
    }
}