package br.com.treinaai.services;

import br.com.planejaai.framework.repository.BaseUserRepository;
import br.com.planejaai.framework.service.GenericPlanService;
import br.com.treinaai.dto.CreateTrainPlanDto;
import br.com.treinaai.entities.TrainPlanEntity;
import br.com.treinaai.repositories.TrainPlanRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;

@Service
public class TrainPlanService extends GenericPlanService<TrainPlanEntity> {

  public TrainPlanService(TrainPlanRepository repository, BaseUserRepository userRepository) {
    super(repository, userRepository);
  }

  @Override
  protected void updateProperties(TrainPlanEntity target, TrainPlanEntity source) {
    if (source.getTitle() != null) {
      target.setTitle(source.getTitle());
    }
    if (source.getStartDate() != null) {
      target.setStartDate(source.getStartDate());
    }
    if (source.getEndDate() != null) {
      target.setEndDate(source.getEndDate());
    }
    if (source.getDescricao() != null) {
      target.setDescricao(source.getDescricao());
    }
    if (source.getDiasTreino() != null) {
      target.setDiasTreino(source.getDiasTreino());
    }
    if (source.getDuracao() != null) {
      target.setDuracao(source.getDuracao());
    }
    if (source.getDayActivities() != null) {
      target.setDayActivities(source.getDayActivities());
    }
  }

  @Transactional
  public TrainPlanEntity criarPlano(UUID userId, CreateTrainPlanDto dto) {
    TrainPlanEntity entity = new TrainPlanEntity();
    entity.setTitle(dto.getTitulo());
    entity.setStartDate(dto.getDataInicio());
    entity.setEndDate(dto.getDataTermino());
    entity.setDescricao(dto.getDescricao());
    entity.setDiasTreino(dto.getDiasTreino());
    entity.setDuracao(dto.getDuracao());
    entity.setDayActivities(dto.getAtividades());

    return atualizarPlano(userId, entity);
  }

  // Assinatura específica que retorna List<TrainPlanEntity> sem sobrescrever o método do framework
  public List<TrainPlanEntity> listarPlanosTreino(UUID userId) throws UsuarioNaoEncontradoException {
    return (List<TrainPlanEntity>) (List<?>) super.listarPlanos(userId);
  }
}
