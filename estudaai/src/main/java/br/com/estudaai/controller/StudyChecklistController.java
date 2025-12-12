package br.com.estudaai.controller;

import br.com.estudaai.entity.StudyChecklist;
import br.com.estudaai.service.StudyChecklistService;
import br.com.planejaai.framework.controller.ChecklistController;

public class StudyChecklistController extends ChecklistController<StudyChecklist>{

   public StudyChecklistController(StudyChecklistService service){
     super(service);
   }

}
