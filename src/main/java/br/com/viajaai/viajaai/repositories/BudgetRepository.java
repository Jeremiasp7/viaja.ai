package br.com.viajaai.viajaai.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.viajaai.viajaai.entities.BudgetEntity;
import br.com.viajaai.viajaai.entities.TravelPlanEntity;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, UUID> {
    TravelPlanEntity findByTravelPlanId(UUID travelPlanId);

    List<BudgetEntity> findByUserId(UUID userId);
}
