package br.com.planejaai.framework.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.planejaai.framework.entity.ResourcesEntityAbstract;

public interface ResourcesRepository extends JpaRepository<ResourcesEntityAbstract, UUID> {
    
    ResourcesEntityAbstract findByGenericPlan(UUID genericPlan);
    List<ResourcesEntityAbstract> findByUserId(UUID userId);
}
