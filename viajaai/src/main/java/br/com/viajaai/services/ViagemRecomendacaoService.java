package br.com.viajaai.services;

import br.com.planejaai.framework.strategy.ObjectRecommendationService;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ViagemRecomendacaoService extends ObjectRecommendationService<String> {

  public ViagemRecomendacaoService(ChatClient chatClient) {
    super(chatClient);
  }

  public List<String> gerarListaDeItens(String contexto) {
    String prompt = String.format(
        "Atue como um assistente de viagens. Crie uma lista de itens essenciais para: '%s'. " +
        "Retorne apenas os nomes dos itens, sem numeração ou texto introdutório.", 
        contexto);
    
    return this.execute(prompt);
  }
}