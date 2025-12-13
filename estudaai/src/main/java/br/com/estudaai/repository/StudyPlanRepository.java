package br.com.estudaai.repository;

import br.com.estudaai.entity.StudyPlanEntity;
import br.com.planejaai.framework.repository.GenericPlanRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface StudyPlanRepository extends GenericPlanRepository<StudyPlanEntity> {}
