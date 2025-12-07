package br.com.viajaai.viajaai.repositories;

import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPlanRepository extends JpaRepository<TravelPlanEntity, UUID> {
  List<TravelPlanEntity> findByUserId(UUID userId);
}
