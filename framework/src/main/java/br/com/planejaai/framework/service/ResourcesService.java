package br.com.planejaai.framework.service;

import br.com.planejaai.framework.entity.ResourcesEntityAbstract;
import br.com.planejaai.framework.exception.ResourceNotFoundException;
import br.com.planejaai.framework.repository.ResourcesRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public abstract class ResourcesService<T extends ResourcesEntityAbstract> {

  protected final ResourcesRepository resourcesRepository;

  protected ResourcesService(ResourcesRepository resourcesRepository) {
    this.resourcesRepository = resourcesRepository;
  }

  public abstract T create(T resource);

  protected T save(T resource) {
    if (resource == null) throw new IllegalArgumentException("Resource cannot be null");
    T saved = (T) resourcesRepository.save(resource);
    return saved;
  }

  public T findById(UUID id) {
    @SuppressWarnings("unchecked")
    T result =
        (T)
            resourcesRepository
                .findById(id)
                .orElseThrow(
                    () -> new ResourceNotFoundException("Resource not found with id: " + id));
    return result;
  }

  public Optional<T> findOptionalById(UUID id) {
    @SuppressWarnings("unchecked")
    Optional<T> opt = (Optional<T>) resourcesRepository.findById(id);
    return opt;
  }

  public T findByPlanId(UUID planId) {
    @SuppressWarnings("unchecked")
    T resource = (T) resourcesRepository.findByPlanId(planId);
    if (resource == null) {
      throw new ResourceNotFoundException("Resource not found for plan id: " + planId);
    }
    return resource;
  }

  @SuppressWarnings("unchecked")
  public List<T> findByUserId(UUID userId) {
    return (List<T>) resourcesRepository.findByUserId(userId);
  }

  @SuppressWarnings("unchecked")
  public List<T> listAll() {
    return (List<T>) resourcesRepository.findAll();
  }

  @Transactional
  public T update(UUID id, T updated) {
    ResourcesEntityAbstract existing =
        resourcesRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));

    if (updated.getMainResource() != null) existing.setMainResource(updated.getMainResource());
    if (updated.getResourceType() != null) existing.setResourceType(updated.getResourceType());
    if (updated.getPlan() != null) existing.setPlan(updated.getPlan());
    if (updated.getUser() != null) existing.setUser(updated.getUser());

    @SuppressWarnings("unchecked")
    T saved = (T) resourcesRepository.save(existing);
    return saved;
  }

  public void delete(UUID id) {
    if (!resourcesRepository.existsById(id)) {
      throw new ResourceNotFoundException("Resource not found with id: " + id);
    }
    resourcesRepository.deleteById(id);
  }
}
