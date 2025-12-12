package br.com.treinaai.controllers;

import br.com.treinaai.dto.CreateTrainPlanDto;
import br.com.treinaai.entities.TrainPlanEntity;
import br.com.treinaai.services.TrainPlanService;
import br.com.treinaai.dto.TrainPlanResponseDto;
import jakarta.validation.Valid;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/train-plans")
public class TrainPlanController {

  private final TrainPlanService trainPlanService;

  public TrainPlanController(TrainPlanService service) {
    this.trainPlanService = service;
  }

  @PostMapping("/{userId}")
  public TrainPlanEntity create(
      @PathVariable("userId") UUID userId, @Valid @RequestBody CreateTrainPlanDto dto) {
    return trainPlanService.criarPlano(userId, dto);
  }

  // Rota explícita para listar planos de treino por usuário
  @GetMapping("/user/{userId}")
  public List<TrainPlanResponseDto> listByUser(@PathVariable("userId") UUID userId) throws UsuarioNaoEncontradoException {
    List<TrainPlanEntity> plans = trainPlanService.listarPlanosTreino(userId);
    return plans.stream().map(this::toDto).collect(Collectors.toList());
  }

  private TrainPlanResponseDto toDto(TrainPlanEntity e) {
    return TrainPlanResponseDto.builder()
        .id(e.getId())
        .titulo(e.getTitle())
        .dataInicio(e.getStartDate())
        .dataTermino(e.getEndDate())
        .descricao(e.getDescricao())
        .diasTreino(e.getDiasTreino())
        .duracao(e.getDuracao())
        .atividades(e.getDayActivities())
        .build();
  }
}
