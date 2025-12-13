package br.com.estudaai.service;

import br.com.estudaai.entity.StudyPlanEntity;
import br.com.estudaai.repository.StudyPlanRepository;
import br.com.estudaai.repository.UserRepository;
import br.com.planejaai.framework.service.GenericPlanService;
import org.springframework.stereotype.Service;

@Service
public class StudyPlanService extends GenericPlanService<StudyPlanEntity> {

  public StudyPlanService(StudyPlanRepository studyRepository, UserRepository planRepository) {
    super(studyRepository, planRepository);
  }

  @Override
  protected void updateProperties(StudyPlanEntity target, StudyPlanEntity source) {
    throw new UnsupportedOperationException("Unimplemented method 'updateProperties'");
  }
}
