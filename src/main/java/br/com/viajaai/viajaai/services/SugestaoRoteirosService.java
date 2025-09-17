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
                O usuário quer uma viagem %s, acomodado em um %s, em uma cidade com o clima %s. Ele possui %s para gastar com a viagem. Vai viajar por %s dias com %s. Ele pode viajar em uma das seguintes datas %s.
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
                Haja como se você fosse uma agência brasileira de viagens de excelência e sugira um roteiro de viagens para um cliente com base nas preferências abaixo: 
                
                %s.
                
                """.formatted(textoDePreferencias);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
