package br.com.viajaai.viajaai.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.CreateUserDto;
import br.com.viajaai.viajaai.dto.PreferencesUserDto;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

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

    public UsersPreferencesEntity atualizarPreferencias(UUID userId, PreferencesUserDto dto) {
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
}
