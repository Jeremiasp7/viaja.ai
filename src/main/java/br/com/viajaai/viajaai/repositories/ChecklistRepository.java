package br.com.viajaai.viajaai.repositories;

import br.com.viajaai.viajaai.entities.ChecklistEntity;
import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository extends JpaRepository<ChecklistEntity, UUID> {
  List<ChecklistEntity> findByTravelPlan(TravelPlanEntity travelPlan);
}
