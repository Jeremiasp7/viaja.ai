package br.com.treinaai.repositories;

import org.springframework.stereotype.Repository;

import br.com.planejaai.framework.repository.ChecklistRepository;

@Repository
public interface TrainChecklistRepository extends ChecklistRepository<br.com.treinaai.entities.TrainChecklistEntity> {

}
