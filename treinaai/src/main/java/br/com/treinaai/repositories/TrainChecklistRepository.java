package br.com.treinaai.repositories;

import br.com.planejaai.framework.repository.ChecklistRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainChecklistRepository
    extends ChecklistRepository<br.com.treinaai.entities.TrainChecklistEntity> {}
