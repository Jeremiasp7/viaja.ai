package br.com.planejaai.framework.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;

@Repository
public interface GenericPlanRepository extends JpaRepository<GenericPlanEntityAbstract, UUID>{
    List<GenericPlanEntityAbstract> findByUserId(UUID userId);
}
