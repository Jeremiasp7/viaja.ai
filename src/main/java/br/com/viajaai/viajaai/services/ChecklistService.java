package br.com.viajaai.viajaai.services;

import br.com.viajaai.viajaai.dto.ChecklistResponseDto;
import br.com.viajaai.viajaai.dto.CreateChecklistDto;
import br.com.viajaai.viajaai.entities.ChecklistEntity;
import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import br.com.viajaai.viajaai.exceptions.ChecklistNaoEncontradoException;
import br.com.viajaai.viajaai.exceptions.TravelPlanNaoEncontradoException;
import br.com.viajaai.viajaai.repositories.ChecklistRepository;
import br.com.viajaai.viajaai.repositories.TravelPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final TravelPlanRepository travelPlanRepository;

    public ChecklistResponseDto criarChecklist(CreateChecklistDto dto) {
        TravelPlanEntity travelPlan = travelPlanRepository.findById(UUID.fromString(dto.getTravelPlanId()))
                .orElseThrow(() -> new TravelPlanNaoEncontradoException("Plano de viagem não encontrado"));

        ChecklistEntity checklist = ChecklistEntity.builder()
                .nome(dto.getNome())
                .concluido(dto.isConcluido())
                .travelPlan(travelPlan)
                .build();

        ChecklistEntity saved = checklistRepository.save(checklist);
        return toDto(saved);
    }

    public List<ChecklistResponseDto> listarPorPlano(UUID travelPlanId) {
        TravelPlanEntity travelPlan = travelPlanRepository.findById(travelPlanId)
                .orElseThrow(() -> new TravelPlanNaoEncontradoException("Plano de viagem não encontrado"));

        return checklistRepository.findByTravelPlan(travelPlan)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ChecklistResponseDto buscarPorId(UUID id) {
        ChecklistEntity checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new ChecklistNaoEncontradoException("Checklist não encontrado"));
        return toDto(checklist);
    }

    public ChecklistResponseDto atualizar(UUID id, CreateChecklistDto dto) {
        ChecklistEntity checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checklist não encontrado"));

        checklist.setNome(dto.getNome());
        checklist.setConcluido(dto.isConcluido());

        ChecklistEntity updated = checklistRepository.save(checklist);
        return toDto(updated);
    }

    public void deletar(UUID id) {
        ChecklistEntity checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new ChecklistNaoEncontradoException("Checklist não encontrado"));
        checklistRepository.delete(checklist);
    }
    
    private ChecklistResponseDto toDto(ChecklistEntity entity) {
        return new ChecklistResponseDto(
                entity.getId(),
                entity.getNome(),
                entity.isConcluido(),
                entity.getTravelPlan().getId()
        );
    }
}
