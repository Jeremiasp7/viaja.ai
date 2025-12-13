package br.com.estudaai.controller;

import br.com.estudaai.entity.StudyPlanEntity;
import br.com.estudaai.service.StudyPlanService;
import br.com.planejaai.framework.controller.GenericPlanController;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudyPlanController extends GenericPlanController<StudyPlanEntity> {
  public StudyPlanController(StudyPlanService service) {
    super(service);
  }

  @Override
  @PostMapping("/{userId}")
  public StudyPlanEntity create(@PathVariable UUID userId, @RequestBody StudyPlanEntity plan) {
    return super.create(userId, plan);
  }

  @Override
  @PutMapping("/{userId}")
  public StudyPlanEntity update(@PathVariable UUID userId, @RequestBody StudyPlanEntity plan) {
    return super.update(userId, plan);
  }
}
