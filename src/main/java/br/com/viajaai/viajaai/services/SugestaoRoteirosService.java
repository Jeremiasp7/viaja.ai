package br.com.viajaai.viajaai.services;

import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.repositories.UserRepository;

@Service
public class SugestaoRoteirosService {
    
    private final ChatClient chatClient;
    private final UserRepository userRepository;

    public SugestaoRoteirosService(ChatClient.Builder chatClientBuilder, UserRepository userRepository) {
        this.chatClient = chatClientBuilder.build();
        this.userRepository = userRepository;
    }

    public String gerarRoteiro(UUID userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UsersPreferencesEntity preferencias = user.getPreferences();

        String textoDePreferencias = """
                Para a minha viagem, atualmente prefiro um estilo %s e tem uma preferência por ficar hospedado em %s. À respeito do clima, prefiro locais com o clima %s, ou que pelo menos algo próximo disso. Financeiramente, tenho disponível %s para fazer essa viagem, que será, preferencialmente de %s dias. Irei para essa viagem com %s. Por fim, tenho as seguintes datas disponível para a viagem: %s.
                """.formatted(
                    preferencias.getEstiloDeViagem(),
                    preferencias.getPreferenciaDeAcomodacao(),
                    preferencias.getPreferenciaDeClima(),
                    preferencias.getFaixaOrcamentaria(),
                    preferencias.getDuracaoDaViagem(),
                    preferencias.getCompanhiaDeViagem(),
                    preferencias.getPreferenciaDeDatas()
                );

        String prompt = """
                Gere uma sugestão de roteiro de viagens básico, baseado nas preferências do usuário descritas abaixo do usuário, %s.
                
                %s.

                Baseado no texto acima, quero que você monte um roteiro de viagens que seja coerente com o que o usuário informou. O roteiro de viagens deve conter: Uma localização, sugestões de atrações turísticas nessa localização, o que o usuário e as companhias (caso ele não vá sozinho) podem fazer nos dias em que ele estiver viajando. No roteiro, deve informar quais deverão ser as atividades do usuário por dia. Lembrando que a quantidade MÁXIMA de dias é a quantidade de dias que ele informou. Faça o roteiro.
                """.formatted(user.getNome(), textoDePreferencias);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
