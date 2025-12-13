package br.com.estudaai.repository;

import br.com.estudaai.entity.StudyChecklist;
import br.com.planejaai.framework.repository.ChecklistRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudyChecklistRepository extends ChecklistRepository<StudyChecklist> {}
