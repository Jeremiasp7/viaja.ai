package br.com.treinaai.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planejaai.framework.controller.ResourcesController;
import br.com.treinaai.entities.TrainResourcesEntity;
import br.com.treinaai.services.TrainResourcesService;

@RestController
@RequestMapping("/api/resources")
public class TrainResourcesController extends ResourcesController<TrainResourcesEntity> {

    private final TrainResourcesService trainResourcesService;

    public TrainResourcesController(TrainResourcesService service) {
        super(service);
        this.trainResourcesService = service;
    }

    @PostMapping
    public TrainResourcesEntity create(@RequestBody TrainResourcesEntity resource) {
        return trainResourcesService.create(resource);
    }

    @GetMapping("/{id}")
    public TrainResourcesEntity getById(@PathVariable UUID id) {
        return trainResourcesService.findById(id);
    }

    @GetMapping("/optional/{id}")
    public Optional<TrainResourcesEntity> getOptionalById(@PathVariable UUID id) {
        return trainResourcesService.findOptionalById(id);
    }

    @GetMapping("/plan/{planId}")
    public TrainResourcesEntity getByPlan(@PathVariable UUID planId) {
        return trainResourcesService.findByPlanId(planId);
    }

    @GetMapping("/user/{userId}")
    public List<TrainResourcesEntity> getByUser(@PathVariable UUID userId) {
        return trainResourcesService.findByUserId(userId);
    }

    @GetMapping
    public List<TrainResourcesEntity> listAll() {
        return trainResourcesService.listAll();
    }

    @PutMapping("/{id}")
    public TrainResourcesEntity update(@PathVariable UUID id, @RequestBody TrainResourcesEntity resource) {
        return trainResourcesService.update(id, resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        trainResourcesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
