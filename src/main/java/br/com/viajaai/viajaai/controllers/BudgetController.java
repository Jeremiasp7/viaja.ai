package br.com.viajaai.viajaai.controllers;

import br.com.viajaai.viajaai.dto.CreateBudgetDto;
import br.com.viajaai.viajaai.entities.BudgetEntity;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.services.BudgetService;
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

@RestController
@RequestMapping("/orcamentos")
public class BudgetController {

  private final BudgetService budgetService;

  public BudgetController(BudgetService budgetService) {
    this.budgetService = budgetService;
  }

  @PostMapping
  public ResponseEntity<BudgetEntity> createBudget(@RequestBody CreateBudgetDto dto) {
    BudgetEntity newBudget = budgetService.createBudget(dto);
    return new ResponseEntity<>(newBudget, HttpStatus.CREATED);
  }

  @GetMapping("/usuario/{userId}")
  public ResponseEntity<List<BudgetEntity>> getBudgetByUser(@PathVariable UUID userId)
      throws UsuarioNaoEncontradoException {
    List<BudgetEntity> budgets = budgetService.getBudgetsByUserId(userId);
    return ResponseEntity.ok(budgets);
  }

  @GetMapping("/plano/{travelPlanId}")
  public ResponseEntity<BudgetEntity> getBudgetByTravelPlanId(@PathVariable UUID travelPlanId) {
    BudgetEntity budget = budgetService.getBudgetByTravelPlanId(travelPlanId);
    return ResponseEntity.ok(budget);
  }

  @GetMapping("/{budgetId}")
  public ResponseEntity<BudgetEntity> getBudgetById(@PathVariable UUID budgetId) {
    BudgetEntity budget = budgetService.getBudgetById(budgetId);
    return ResponseEntity.ok(budget);
  }

  @PutMapping("/{budgetId}")
  public ResponseEntity<BudgetEntity> updateBudget(
      @PathVariable UUID budgetId, @RequestBody CreateBudgetDto dto) {
    BudgetEntity updatedBudget = budgetService.updateBudget(budgetId, dto);
    return ResponseEntity.ok(updatedBudget);
  }

  @DeleteMapping("/{budgetId}")
  public ResponseEntity<Void> deleteBudget(@PathVariable UUID budgetId) {
    budgetService.deleteBudget(budgetId);
    return ResponseEntity.noContent().build();
  }
}
