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
import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import br.com.viajaai.viajaai.services.TravelPlanService;

@RestController
@RequestMapping("/planos")
public class TravelPlanController {

    private final TravelPlanService travelPlanService;
    public TravelPlanController(TravelPlanService travelPlanService){
			this.travelPlanService = travelPlanService;
		}

    @PostMapping
    public ResponseEntity<TravelPlanEntity> createTravelPlan(@RequestBody CreateTravelPlanDto dto) {
        TravelPlanEntity newPlan = travelPlanService.createTravelPlan(dto);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<TravelPlanEntity>> getTravelPlansByUser(@PathVariable UUID userId) {
        List<TravelPlanEntity> plans = travelPlanService.getTravelPlansByUserId(userId);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPlanEntity> getTravelPlanById(@PathVariable UUID id) {
        TravelPlanEntity plan = travelPlanService.getTravelPlanById(id);
        return ResponseEntity.ok(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelPlanEntity> updateTravelPlan(@PathVariable UUID id, @RequestBody CreateTravelPlanDto dto) {
        TravelPlanEntity updatedPlan = travelPlanService.updateTravelPlan(id, dto);
        return ResponseEntity.ok(updatedPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravelPlan(@PathVariable UUID id) {
        travelPlanService.deleteTravelPlan(id);
        return ResponseEntity.noContent().build();
    }
}
