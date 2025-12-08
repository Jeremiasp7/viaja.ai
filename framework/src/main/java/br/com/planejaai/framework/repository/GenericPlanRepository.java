package br.com.planejaai.framework.repository;

import java.util.List;
import java.util.Optional;
import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericPlanRepository<T extends GenericPlanEntityAbstract>
    extends JpaRepository<T, UUID> {
    Optional<T> findByUserId(UUID userId);
}
