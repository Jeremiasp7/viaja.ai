package br.com.viajaai.viajaai.repositories;

import br.com.viajaai.viajaai.entities.ChecklistEntity;
import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ChecklistRepository extends JpaRepository<ChecklistEntity, UUID> {
    List<ChecklistEntity> findByTravelPlan(TravelPlanEntity travelPlan);
}
