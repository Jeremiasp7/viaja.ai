package br.com.viajaai.services;

import br.com.planejaai.framework.entity.BaseUserEntity;
import br.com.planejaai.framework.entity.GenericPlanEntityAbstract;
import br.com.planejaai.framework.entity.UserPreferencesEntityAbstract;
import br.com.planejaai.framework.repository.BaseUserRepository;
import br.com.planejaai.framework.strategy.PlanSugestionService;
import br.com.viajaai.entities.ViagemBudget;
import br.com.viajaai.entities.ViagemPlan;
import br.com.viajaai.entities.ViagemPreferences;
import br.com.viajaai.repositories.ViagemPlanRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ViagemSugestaoRoteiro extends PlanSugestionService {

  public ViagemSugestaoRoteiro(
      ChatClient chatClient,
      BaseUserRepository userRepository,
      ViagemPlanRepository viagemPlanRepository) {
    super(chatClient, userRepository, viagemPlanRepository);
  }

  @Override
  protected String getAdditionalPlanDetails(GenericPlanEntityAbstract plan) {
    if (plan instanceof ViagemPlan viagem) {
      StringBuilder details = new StringBuilder();
      
      details.append(String.format("- Destino: %s\n", viagem.getDestino()));
      details.append(String.format("- Estilo da Viagem: %s\n", viagem.getEstiloViagem()));
      
      if (viagem.getNumeroPessoas() != null) {
        details.append(String.format("- Número de Pessoas: %d\n", viagem.getNumeroPessoas()));
      }

      if (viagem.getResources() instanceof ViagemBudget budget) {
        details.append(String.format("- Orçamento Total: %s %.2f\n", 
            budget.getResourceType(), budget.getMainResource()));
        details.append(String.format("- Já gasto: %.2f\n", budget.getValorGasto()));
      }

      return details.toString();
    }
    return "";
  }

  @Override
  protected String buildPromptForPreferences(
      BaseUserEntity user, UserPreferencesEntityAbstract preferences, String userPrompt) {
    
    String basePrompt = super.buildPromptForPreferences(user, preferences, userPrompt);
    
    StringBuilder stringBuilder = new StringBuilder(basePrompt);

    if (preferences instanceof ViagemPreferences preferencias) {
      stringBuilder.append("\n[Contexto de Preferências de Viagem]:");
      
      if (preferencias.getAssentoPreferencia() != null) {
        stringBuilder.append("\n- Preferência de assento: ").append(preferencias.getAssentoPreferencia());
      }
      
      if (preferencias.getRestricoesAlimentares() != null && !preferencias.getRestricoesAlimentares().isBlank()) {
        stringBuilder.append("\n- Restrições Alimentares: ").append(preferencias.getRestricoesAlimentares());
      }
      
      stringBuilder.append("\n- Aceita escalas? ").append(preferencias.isAceitaEscalas() ? "Sim" : "Não");
    }

    return stringBuilder.toString();
  }
}