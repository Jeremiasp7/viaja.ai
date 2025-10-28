package br.com.viajaai.viajaai.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.CreateBudgetDto;
import br.com.viajaai.viajaai.entities.BudgetEntity;
import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.repositories.BudgetRepository;
import br.com.viajaai.viajaai.repositories.TravelPlanRepository;
import br.com.viajaai.viajaai.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;
    
    public BudgetEntity createBudget(CreateBudgetDto dto) {
        if (budgetRepository.findByTravelPlanId(dto.getTravelPlanId()) != null) {
            throw new IllegalArgumentException("Já existe um orçamento para este plano de viagem.");
        }

        TravelPlanEntity travelPlan = travelPlanRepository.findById(dto.getTravelPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plano de viagem não encontrado com o ID: " + dto.getTravelPlanId()));

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + dto.getTravelPlanId()));

        BudgetEntity budget = BudgetEntity.builder()
                .totalAmount(dto.getTotalAmount())
                .currency(dto.getCurrency())
                .categories(dto.getCategories())
                .travelPlan(travelPlan)
                .user(user)
                .build();
        
        return budgetRepository.save(budget);
    }

    public List<BudgetEntity> getBudgetsByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
             throw new EntityNotFoundException("Usuário não encontrado com o ID: " + userId);
        }

        return budgetRepository.findByUserId(userId);
    }

    public BudgetEntity getBudgetByTravelPlanId(UUID travelPlanId) {
        BudgetEntity budget = budgetRepository.findByTravelPlanId(travelPlanId);
    
        if (budget == null) {
            throw new EntityNotFoundException("Orçamento não encontrado para o plano de viagem com o ID: " + travelPlanId);
        }

        return budget;
    }

    public BudgetEntity getBudgetById(UUID budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Orçamento não encontrado com o ID: " + budgetId));
    }

    @Transactional
    public BudgetEntity updateBudget(UUID budgetId, CreateBudgetDto dto) {
        BudgetEntity existingBudget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Orçamento não encontrado com o ID: " + budgetId));

        existingBudget.setTotalAmount(dto.getTotalAmount());
        existingBudget.setCurrency(dto.getCurrency());
        existingBudget.setCategories(dto.getCategories());

        return budgetRepository.save(existingBudget);
    }

    public void deleteBudget(UUID budgetId) {
        if (!budgetRepository.existsById(budgetId)) {
            throw new EntityNotFoundException("Orçamento não encontrado com o ID: " + budgetId);
        }
        budgetRepository.deleteById(budgetId);
    }
}
