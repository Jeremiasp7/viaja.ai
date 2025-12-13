package br.com.planejaai.framework.controller;

import br.com.planejaai.framework.entity.ChecklistEntityAbstract;
import br.com.planejaai.framework.service.ChecklistService;
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

public abstract class ChecklistController<T extends ChecklistEntityAbstract> {

  protected final ChecklistService<T> service;

  protected ChecklistController(ChecklistService<T> service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<T> create(@RequestBody T entity) {
    return ResponseEntity.ok(service.create(entity));
  }

  @GetMapping("/plan/{planId}")
  public ResponseEntity<List<T>> listByPlan(@PathVariable UUID planId) {
    return ResponseEntity.ok(service.findByPlanId(planId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<T> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<T> update(@PathVariable UUID id, @RequestBody T entity) {
    return ResponseEntity.ok(service.update(id, entity));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
