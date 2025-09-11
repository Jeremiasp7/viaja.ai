package br.com.viajaai.viajaai.services;

import java.util.List;
import java.util.UUID;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.CreateUserDto;
import br.com.viajaai.viajaai.dto.CreateUserPreferencesDto;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.repositories.UserRepository;
import br.com.viajaai.viajaai.repositories.UsersPreferencesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    // o @RequiredArgsConstructor cria o construtor automaticamente
    private final UserRepository userRepository;
    private final UsersPreferencesRepository usersPreferencesRepository;
    
    public UserEntity criarUsuario(CreateUserDto dto) {
        UserEntity user = UserEntity.builder()
            .email(dto.getEmail())
            .nome(dto.getNome())
            .senha(dto.getSenha())
            .build();
        
        return userRepository.save(user);
    }

    public List<UserEntity> buscarUsuarios() {
        return userRepository.findAll();
    }

    public UsersPreferencesEntity atualizarPreferencias(UUID userId, CreateUserPreferencesDto dto) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UsersPreferencesEntity preferences = user.getPreferences();

        if (preferences == null) {
            preferences = new UsersPreferencesEntity();
            preferences.setUser(user);
        }

        preferences.setEstiloDeViagem(dto.getEstiloDeViagem());
        preferences.setPreferenciaDeAcomodacao(dto.getPreferenciaDeAcomodacao());
        preferences.setPreferenciaDeClima(dto.getPreferenciaDeClima());
        preferences.setFaixaOrcamentaria(dto.getFaixaOrcamentaria());
        preferences.setDuracaoDaViagem(dto.getDuracaoDaViagem());
        preferences.setCompanhiaDeViagem(dto.getCompanhiaDeViagem());
        preferences.setPreferenciaDeDatas(dto.getPreferenciaDeDatas());

        user.setPreferences(preferences);

        userRepository.save(user);

        return preferences;
    }

    public UsersPreferencesEntity buscarPreferenciasDoUsuario(UUID userId) {
        return usersPreferencesRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Este usuário não possui preferências"));
    }
}
