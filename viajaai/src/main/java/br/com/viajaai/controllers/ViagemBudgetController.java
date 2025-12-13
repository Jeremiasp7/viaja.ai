package br.com.viajaai.controllers;

import br.com.planejaai.framework.controller.ResourcesController;
import br.com.viajaai.entities.ViagemBudget;
import br.com.viajaai.services.ViagemBudgetService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viagens/budgets")
public class ViagemBudgetController extends ResourcesController<ViagemBudget> {

  public ViagemBudgetController(ViagemBudgetService service) {
    super(service);
  }


  @Override
  @GetMapping("/{id}")
  public ViagemBudget getById(@PathVariable("id") UUID id) {
    return super.getById(id);
  }

  @Override
  @GetMapping("/optional/{id}")
  public Optional<ViagemBudget> getOptionalById(@PathVariable("id") UUID id) {
    return super.getOptionalById(id);
  }

  @Override
  @GetMapping("/plan/{planId}")
  public ViagemBudget getByPlan(@PathVariable("planId") UUID planId) {
    return super.getByPlan(planId);
  }

  @Override
  @GetMapping("/user/{userId}")
  public List<ViagemBudget> getByUser(@PathVariable("userId") UUID userId) {
    return super.getByUser(userId);
  }

  @Override
  @PutMapping("/{id}")
  public ViagemBudget update(@PathVariable("id") UUID id, @RequestBody ViagemBudget resource) {
    return super.update(id, resource);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
    return super.delete(id);
  }
}