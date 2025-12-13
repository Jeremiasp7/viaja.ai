package br.com.viajaai.controllers;

import br.com.planejaai.framework.controller.ChecklistController;
import br.com.viajaai.entities.ViagemChecklist;
import br.com.viajaai.services.ViagemChecklistService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viagens/checklists")
public class ViagemChecklistController extends ChecklistController<ViagemChecklist> {

  public ViagemChecklistController(ViagemChecklistService service) {
    super(service);
  }


  @Override
  @GetMapping("/plan/{planId}")
  public ResponseEntity<List<ViagemChecklist>> listByPlan(@PathVariable("planId") UUID planId) {
    return super.listByPlan(planId);
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<ViagemChecklist> getById(@PathVariable("id") UUID id) {
    return super.getById(id);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<ViagemChecklist> update(@PathVariable("id") UUID id, @RequestBody ViagemChecklist entity) {
    return super.update(id, entity);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
    return super.delete(id);
  }
}