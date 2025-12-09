package br.com.treinaai.controllers;

import br.com.planejaai.framework.controller.ChecklistController;
import br.com.treinaai.entities.TrainChecklistEntity;
import br.com.treinaai.services.TrainChecklistService;
import java.util.List;
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

@RestController
@RequestMapping("/api/checklists")
public class TrainChecklistController extends ChecklistController<TrainChecklistEntity> {

  private final TrainChecklistService service;

  public TrainChecklistController(TrainChecklistService service) {
    super(service);
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<TrainChecklistEntity> create(@RequestBody TrainChecklistEntity entity) {
    return ResponseEntity.ok(service.create(entity));
  }

  @GetMapping("/plan/{planId}")
  public ResponseEntity<List<TrainChecklistEntity>> listByPlan(@PathVariable UUID planId) {
    return ResponseEntity.ok(service.findByPlanId(planId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TrainChecklistEntity> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TrainChecklistEntity> update(
      @PathVariable UUID id, @RequestBody TrainChecklistEntity entity) {
    return ResponseEntity.ok(service.update(id, entity));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
