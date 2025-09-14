package br.com.viajaai.viajaai.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.viajaai.viajaai.entities.TravelPlanEntity;

@Repository
public interface TravelPlanRepository extends JpaRepository<TravelPlanEntity, UUID> {
    List<TravelPlanEntity> findByUserId(UUID userId);
}
