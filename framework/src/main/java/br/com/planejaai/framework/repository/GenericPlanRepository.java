package br.com.planejaai.framework.repository;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericPlanRepository<T extends GenericPlanEntityAbstract>
    extends JpaRepository<T, UUID> {
  List<T> findByUserId(UUID userId);
}
