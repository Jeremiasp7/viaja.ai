package br.com.treinaai.strategy;

import br.com.planejaai.framework.repository.BaseUserRepository;
import br.com.planejaai.framework.repository.GenericPlanRepository;
import br.com.planejaai.framework.strategy.PlanSugestionService;
import br.com.treinaai.entities.TrainPlanEntity;
import br.com.treinaai.entities.PreferencesEntity;
import br.com.treinaai.repositories.TrainPlanRepository;
import br.com.treinaai.repositories.PreferencesRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TrainPlanSuggestionService extends PlanSugestionService {

  private final PreferencesRepository preferencesRepository;
  private final TrainPlanRepository trainPlanRepository;

  public TrainPlanSuggestionService(
      ChatClient chatClient,
      BaseUserRepository userRepository,
      TrainPlanRepository trainPlanRepository,
      PreferencesRepository preferencesRepository) {
    super(chatClient, userRepository, trainPlanRepository);
    this.preferencesRepository = preferencesRepository;
    this.trainPlanRepository = trainPlanRepository;
  }

  protected String buildPromptForPreferences(
      br.com.planejaai.framework.entity.BaseUserEntity user,
      br.com.planejaai.framework.entity.UserPreferencesEntityAbstract preferences,
      String userPrompt) {
    
    PreferencesEntity prefs = (PreferencesEntity) preferences;
    
    StringBuilder sb = new StringBuilder();
    sb.append("Você é um especialista em planejamento de treino de exercícios físicos. ");
    sb.append("Responda em português de forma direta e prática.\n\n");
    sb.append("Preferências do usuário:\n");
    
    if (prefs.getTipoDeTreino() != null) {
      sb.append("- Tipo de Treino: ").append(prefs.getTipoDeTreino()).append("\n");
    }
    if (prefs.getDuracaoTreino() != null) {
      sb.append("- Duração do Treino: ").append(prefs.getDuracaoTreino()).append(" minutos\n");
    }
    if (prefs.getPreferenciaHorarios() != null && !prefs.getPreferenciaHorarios().isEmpty()) {
      sb.append("- Horários Disponíveis: ").append(prefs.getPreferenciaHorarios()).append("\n");
    }
    if (prefs.getAtividadeRealizada() != null) {
      sb.append("- Atividade Realizada Recentemente: ").append(prefs.getAtividadeRealizada()).append("\n");
    }
    
    sb.append("\nSolicitação do usuário: ").append(userPrompt);
    
    return sb.toString();
  }

  protected String buildPlanResume(
      br.com.planejaai.framework.entity.GenericPlanEntityAbstract plan) {
    TrainPlanEntity trainPlan = (TrainPlanEntity) plan;
    
    return String.format(
        "O usuário possui um plano de treino intitulado \"%s\". "
            + "O plano deve ser executado de %s até %s.",
        trainPlan.getTitle(), trainPlan.getStartDate(), trainPlan.getEndDate());
  }

  protected String getAdditionalPlanDetails(
      br.com.planejaai.framework.entity.GenericPlanEntityAbstract plan) {
    
    TrainPlanEntity trainPlan = (TrainPlanEntity) plan;
    StringBuilder sb = new StringBuilder();
    
    if (trainPlan.getDayActivities() != null && !trainPlan.getDayActivities().isEmpty()) {
      sb.append("Atividades programadas:\n");
      for (int i = 0; i < trainPlan.getDayActivities().size(); i++) {
        sb.append("- Dia ").append(i + 1).append(": ").append(trainPlan.getDayActivities().get(i)).append("\n");
      }
    }
    
    return sb.toString();
  }

  protected String buildPromptForGenericPlan(
      br.com.planejaai.framework.entity.GenericPlanEntityAbstract plan,
      String resumoPlano,
      String additionalDetails) {
    
    String additionalSection =
        additionalDetails.isBlank() ? "" : "\nDetalhes adicionais:\n" + additionalDetails;
    
    String prompt =
        """
        Você é um especialista em planejamento de treino e exercícios físicos. 
        Responda em português e de forma direta, sem dar margem para continuar uma conversa.
        
        Baseado no plano de treino abaixo, monte um plano detalhado e prático com:
        - Exercícios específicos recomendados
        - Série, repetições e descanso para cada exercício
        - Progressão ao longo dos dias
        - Dicas de segurança e forma correta
        - Alimentação complementar (sugestões leves)
        
        %s%s
        """
            .formatted(resumoPlano, additionalSection);
    
    return prompt;
  }
}
