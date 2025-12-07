package br.com.viajaai.viajaai.services;

import br.com.viajaai.viajaai.entities.TravelPlanEntity;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.exceptions.PreferenciasNaoEncontradasException;
import br.com.viajaai.viajaai.exceptions.TravelPlanNaoEncontradoException;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.repositories.TravelPlanRepository;
import br.com.viajaai.viajaai.repositories.UserRepository;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SugestaoRoteirosService {

  private final ChatClient chatClient;
  private final UserRepository userRepository;
  private final TravelPlanRepository travelPlanRepository;

  public SugestaoRoteirosService(
      ChatClient.Builder chatClientBuilder,
      UserRepository userRepository,
      TravelPlanRepository travelPlanRepository) {
    this.chatClient = chatClientBuilder.build();
    this.userRepository = userRepository;
    this.travelPlanRepository = travelPlanRepository;
  }

  public String gerarRoteiroPreferencias(UUID userId) throws UsuarioNaoEncontradoException {
    UserEntity user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

    UsersPreferencesEntity preferencias = user.getPreferences();
    if (preferencias == null) {
      throw new PreferenciasNaoEncontradasException("Usuário não possui preferências cadastradas.");
    }

    String textoDePreferencias =
        """
                O usuário quer uma viagem %s, acomodado em um %s, em uma cidade com o clima %s. Ele possui %s para gastar com a viagem. Vai viajar por %s dias com %s. Ele pode viajar em uma das seguintes datas %s.
                """
            .formatted(
                preferencias.getEstiloDeViagem(),
                preferencias.getPreferenciaDeAcomodacao(),
                preferencias.getPreferenciaDeClima(),
                preferencias.getFaixaOrcamentaria(),
                preferencias.getDuracaoDaViagem(),
                preferencias.getCompanhiaDeViagem(),
                preferencias.getPreferenciaDeDatas());

    String prompt =
        """
                Responda em português e não dê margem para continuar uma conversa. Haja como se você fosse uma agência brasileira de viagens de excelência e sugira um roteiro de viagens para um cliente com base nas preferências abaixo:

                %s.

                """
            .formatted(textoDePreferencias);

    return chatClient.prompt().user(prompt).call().content();
  }

  public String gerarRoteiroTravelPlan(UUID travelPlanId) throws TravelPlanNaoEncontradoException {
    TravelPlanEntity plan =
        travelPlanRepository
            .findById(travelPlanId)
            .orElseThrow(
                () ->
                    new TravelPlanNaoEncontradoException(
                        "Plano de viagem não encontrado com o ID: " + travelPlanId));

    // Monta uma descrição textual detalhada do plano
    String destinosTexto =
        plan.getDestinations().stream()
            .map(
                dest ->
                    "- "
                        + dest.getCity()
                        + ", "
                        + dest.getCountry()
                        + " (chegada: "
                        + dest.getArrivalDate()
                        + ", saída: "
                        + dest.getDepartureDate()
                        + ")")
            .collect(Collectors.joining("\n"));

    String resumoPlano =
        """
                O usuário possui um plano de viagem intitulado "%s".
                Ele planeja viajar de %s até %s, visitando os seguintes destinos:
                %s
                """
            .formatted(plan.getTitle(), plan.getStartDate(), plan.getEndDate(), destinosTexto);

    String prompt =
        """
                Responda em português e de forma direta, sem dar margem para continuar uma conversa.
                Haja como uma agência de viagens de alto padrão e monte um roteiro detalhado
                com sugestões de atividades, atrações, experiências culturais e gastronômicas
                com base no plano de viagem abaixo:

                %s
                """
            .formatted(resumoPlano);

    return chatClient.prompt().user(prompt).call().content();
  }
}
