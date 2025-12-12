package br.com.treinaai.controllers;

import br.com.planejaai.framework.controller.GenericPlanController;
import br.com.treinaai.dto.CreateTrainPlanDto;
import br.com.treinaai.entities.TrainPlanEntity;
import br.com.treinaai.services.TrainPlanService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class TrainPlanController extends GenericPlanController<TrainPlanEntity> {

  private final TrainPlanService trainPlanService;

  public TrainPlanController(TrainPlanService service) {
    super(service);
    this.trainPlanService = service;
  }

  @PostMapping("/create/{userId}")
  public TrainPlanEntity create(@PathVariable UUID userId, @Valid @RequestBody CreateTrainPlanDto dto) {
    TrainPlanEntity plan = new TrainPlanEntity();
    plan.setTitle(dto.getTitulo());
    plan.setDescricao(dto.getDescricao());
    plan.setDiasTreino(dto.getDiasTreino());
    plan.setDuracao(dto.getDuracao());
    return trainPlanService.atualizarPlano(userId, plan);
  }
}
