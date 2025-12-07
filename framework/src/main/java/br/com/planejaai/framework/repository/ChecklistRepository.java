package br.com.planejaai.framework.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import br.com.planejaai.framework.entity.ChecklistEntityAbstract;

@NoRepositoryBean
public interface ChecklistRepository<T extends ChecklistEntityAbstract> extends JpaRepository<T, UUID> {
    
    List<T> findByPlanId(UUID planId);
}