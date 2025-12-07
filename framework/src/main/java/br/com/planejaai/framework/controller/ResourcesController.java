package br.com.planejaai.framework.controller;

import br.com.planejaai.framework.entity.ResourcesEntityAbstract;
import br.com.planejaai.framework.service.ResourcesService;
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

@RequestMapping("/api/resources")
public abstract class ResourcesController<T extends ResourcesEntityAbstract> {

  protected final ResourcesService<T> resourcesService;

  protected ResourcesController(ResourcesService<T> resourcesService) {
    this.resourcesService = resourcesService;
  }

  @PostMapping
  public T create(@RequestBody T resource) {
    return resourcesService.create(resource);
  }

  @GetMapping("/{id}")
  public T getById(@PathVariable UUID id) {
    return resourcesService.findById(id);
  }

  @GetMapping("/optional/{id}")
  public Optional<T> getOptionalById(@PathVariable UUID id) {
    return resourcesService.findOptionalById(id);
  }

  @GetMapping("/plan/{planId}")
  public T getByPlan(@PathVariable UUID planId) {
    return resourcesService.findByPlanId(planId);
  }

  @GetMapping("/user/{userId}")
  public List<T> getByUser(@PathVariable UUID userId) {
    return resourcesService.findByUserId(userId);
  }

  @GetMapping
  public List<T> listAll() {
    return resourcesService.listAll();
  }

  @PutMapping("/{id}")
  public T update(@PathVariable UUID id, @RequestBody T resource) {
    return resourcesService.update(id, resource);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    resourcesService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
