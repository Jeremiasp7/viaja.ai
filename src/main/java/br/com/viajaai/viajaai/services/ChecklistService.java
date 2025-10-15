package br.com.viajaai.viajaai.services;

import br.com.viajaai.viajaai.dto.CreateChecklistDto;
import br.com.viajaai.viajaai.entities.ChecklistEntity;
import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import br.com.viajaai.viajaai.repositories.ChecklistRepository;
import br.com.viajaai.viajaai.repositories.TravelPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final TravelPlanRepository travelPlanRepository;

    public ChecklistEntity criarChecklist(CreateChecklistDto dto) {
        TravelPlanEntity travelPlan = travelPlanRepository.findById(UUID.fromString(dto.getTravelPlanId()))
                .orElseThrow(() -> new RuntimeException("Plano de viagem não encontrado"));

        ChecklistEntity checklist = ChecklistEntity.builder()
                .nome(dto.getNome())
                .concluido(dto.isConcluido())
                .travelPlan(travelPlan)
                .build();

        return checklistRepository.save(checklist);
    }

    public List<ChecklistEntity> listarPorPlano(UUID travelPlanId) {
        TravelPlanEntity travelPlan = travelPlanRepository.findById(travelPlanId)
                .orElseThrow(() -> new RuntimeException("Plano de viagem não encontrado"));
        return checklistRepository.findByTravelPlan(travelPlan);
    }

    public ChecklistEntity buscarPorId(UUID id) {
        return checklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checklist não encontrado"));
    }

    public ChecklistEntity atualizar(UUID id, CreateChecklistDto dto) {
        ChecklistEntity checklist = buscarPorId(id);
        checklist.setNome(dto.getNome());
        checklist.setConcluido(dto.isConcluido());
        return checklistRepository.save(checklist);
    }

    public void deletar(UUID id) {
        ChecklistEntity checklist = buscarPorId(id);
        checklistRepository.delete(checklist);
    }
}
