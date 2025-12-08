package br.com.viajaai.viajaai.repositories;

import br.com.viajaai.viajaai.entities.BudgetEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, UUID> {
  BudgetEntity findByTravelPlanId(UUID travelPlanId);

  List<BudgetEntity> findByUserId(UUID userId);
}
