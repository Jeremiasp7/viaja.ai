package br.com.treinaai.services;

import br.com.planejaai.framework.service.ResourcesService;
import br.com.treinaai.entities.TrainResourcesEntity;
import br.com.treinaai.repositories.TrainResourcesRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TrainResourcesService extends ResourcesService<TrainResourcesEntity> {

  public TrainResourcesService(TrainResourcesRepository repository) {
    super(repository);
  }

  @Override
  public TrainResourcesEntity create(TrainResourcesEntity resource) {
    return save(resource);
  }

  @Override
  public TrainResourcesEntity findById(UUID id) {
    return super.findById(id);
  }

  @Override
  public Optional<TrainResourcesEntity> findOptionalById(UUID id) {
    return super.findOptionalById(id);
  }

  @Override
  public List<TrainResourcesEntity> findByUserId(UUID userId) {
    return super.findByUserId(userId);
  }

  @Override
  public List<TrainResourcesEntity> listAll() {
    return super.listAll();
  }

  @Override
  public TrainResourcesEntity update(UUID id, TrainResourcesEntity updated) {
    return super.update(id, updated);
  }
}
