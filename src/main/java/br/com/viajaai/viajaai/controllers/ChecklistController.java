package br.com.viajaai.viajaai.controllers;

import br.com.viajaai.viajaai.dto.ChecklistResponseDto;
import br.com.viajaai.viajaai.dto.CreateChecklistDto;
import br.com.viajaai.viajaai.services.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService service;

    @PostMapping
    public ChecklistResponseDto criar(@RequestBody CreateChecklistDto dto) {
        return service.criarChecklist(dto);
    }

    @GetMapping("/plano/{travelPlanId}")
    public List<ChecklistResponseDto> listarPorPlano(@PathVariable UUID travelPlanId) {
        return service.listarPorPlano(travelPlanId);
    }

    @GetMapping("/{id}")
    public ChecklistResponseDto buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ChecklistResponseDto atualizar(@PathVariable UUID id, @RequestBody CreateChecklistDto dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}
