package br.com.viajaai.controllers;

import br.com.planejaai.framework.controller.GenericPlanController;
import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import br.com.viajaai.entities.ViagemPlan;
import br.com.viajaai.services.ViagemPlanService;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viagens/plans")
public class ViagemPlanController extends GenericPlanController<ViagemPlan> {

  public ViagemPlanController(ViagemPlanService service) {
    super(service);
  }


  @Override
  @PostMapping("/create/{userId}")
  public ViagemPlan create(@PathVariable("userId") UUID userId, @RequestBody ViagemPlan plan) {
    return super.create(userId, plan);
  }

  @Override
  @PutMapping("/{userId}")
  public ViagemPlan update(@PathVariable("userId") UUID userId, @RequestBody ViagemPlan plan) {
    return super.update(userId, plan);
  }

  @Override
  @GetMapping("/{userId}")
  public List<? extends GenericPlanEntityAbstract> list(@PathVariable("userId") UUID userId) throws UsuarioNaoEncontradoException {
    return super.list(userId);
  }

  @Override
  @DeleteMapping("/{planId}")
  public void delete(@PathVariable("planId") UUID planId) throws Exception {
    super.delete(planId);
  }
}