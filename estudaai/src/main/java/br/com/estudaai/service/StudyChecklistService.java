package br.com.estudaai.service;

import br.com.estudaai.entity.StudyChecklist;
import br.com.estudaai.repository.StudyChecklistRepository;
import br.com.estudaai.repository.StudyPlanRepository;
import br.com.planejaai.framework.service.ChecklistService;

public class StudyChecklistService extends ChecklistService<StudyChecklist>{

  public StudyChecklistService(StudyChecklistRepository studyRepository, StudyPlanRepository planRepository)  {
   super(studyRepository, planRepository);
  }

	@Override
	protected void updateData(StudyChecklist existing, StudyChecklist incoming) {
		throw new UnsupportedOperationException("Unimplemented method 'updateData'");
	}

}
