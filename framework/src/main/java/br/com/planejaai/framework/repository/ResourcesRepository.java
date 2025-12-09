package br.com.planejaai.framework.repository;

import br.com.planejaai.framework.entity.ResourcesEntityAbstract;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcesRepository extends JpaRepository<ResourcesEntityAbstract, UUID> {

  ResourcesEntityAbstract findByPlan_Id(UUID genericPlan);

  List<ResourcesEntityAbstract> findByUserId(UUID userId);
}
