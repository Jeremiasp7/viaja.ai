package br.com.viajaai.viajaai.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.viajaai.viajaai.dto.CreateTravelPlanDto;
import br.com.viajaai.viajaai.dto.TravelPlanResponseDto;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.services.TravelPlanService;

@RestController
@RequestMapping("/planos")
public class TravelPlanController {

    private final TravelPlanService travelPlanService;
    public TravelPlanController(TravelPlanService travelPlanService){
			this.travelPlanService = travelPlanService;
		}

    @PostMapping
    public ResponseEntity<TravelPlanResponseDto> createTravelPlan(@RequestBody CreateTravelPlanDto dto) throws UsuarioNaoEncontradoException {
        TravelPlanResponseDto newPlan = travelPlanService.createTravelPlan(dto);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<TravelPlanResponseDto>> getTravelPlansByUser(@PathVariable UUID userId) throws UsuarioNaoEncontradoException {
        List<TravelPlanResponseDto> plans = travelPlanService.getTravelPlansByUserId(userId);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPlanResponseDto> getTravelPlanById(@PathVariable UUID id) {
        TravelPlanResponseDto plan = travelPlanService.getTravelPlanByIdDto(id);
        return ResponseEntity.ok(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelPlanResponseDto> updateTravelPlan(@PathVariable UUID id, @RequestBody CreateTravelPlanDto dto) {
        TravelPlanResponseDto updatedPlan = travelPlanService.updateTravelPlan(id, dto);
        return ResponseEntity.ok(updatedPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravelPlan(@PathVariable UUID id) {
        travelPlanService.deleteTravelPlan(id);
        return ResponseEntity.noContent().build();
    }
}
