package br.com.planejaai.framework.controller;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import br.com.planejaai.framework.service.GenericPlanService;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/plans")
public abstract class GenericPlanController<T extends GenericPlanEntityAbstract> {

  protected final GenericPlanService<T> service;

  protected GenericPlanController(GenericPlanService<T> service) {
    this.service = service;
  }

  @PutMapping("/create/{userId}")
  public T update(@PathVariable UUID userId, @RequestBody T plan) {
    return service.atualizarPlano(userId, plan);
  }

  @PostMapping("/update/{userId}")
  public T create(@PathVariable UUID userId, @RequestBody T plan) {
    return service.atualizarPlano(userId, plan);
  }

  @GetMapping("/{userId}")
  public List<GenericPlanEntityAbstract> list(@PathVariable UUID userId)
      throws UsuarioNaoEncontradoException {
    return service.listarPlanos(userId);
  }

  @DeleteMapping("/{userId}")
  public void delete(@PathVariable UUID userId) throws Exception {
    service.deleteTravelPlan(userId);
  }
}
