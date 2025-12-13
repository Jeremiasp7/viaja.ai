package br.com.estudaai.service;

import br.com.estudaai.entity.StudyChecklist;
import br.com.estudaai.repository.StudyChecklistRepository;
import br.com.estudaai.repository.StudyPlanRepository;
import br.com.planejaai.framework.service.ChecklistService;
import org.springframework.stereotype.Service;

@Service
public class StudyChecklistService extends ChecklistService<StudyChecklist> {

  public StudyChecklistService(
      StudyChecklistRepository studyRepository, StudyPlanRepository planRepository) {
    super(studyRepository, planRepository);
  }

  @Override
  protected void updateData(StudyChecklist existing, StudyChecklist incoming) {
    if (incoming.getName() != null) {
      existing.setName(incoming.getName());
    }
    if (incoming.getCompleted() != null) {
      existing.setCompleted(incoming.getCompleted());
    }

    if (incoming.getReference() != null) {
      existing.setReference(incoming.getReference());
    }

    if (incoming.getTaskType() != null) {
      existing.setTaskType(incoming.getTaskType());
    }

    if (incoming.getEstimatedMinutes() != null) {
      existing.setEstimatedMinutes(incoming.getEstimatedMinutes());
    }
  }
}
