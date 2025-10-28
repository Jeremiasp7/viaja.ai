package br.com.viajaai.viajaai.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.CreateUserPreferencesDto;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.exceptions.PreferenciasNaoEncontradasException;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.repositories.UserRepository;
import br.com.viajaai.viajaai.repositories.UsersPreferencesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPreferencesService {
    private final UserRepository userRepository;
    private final UsersPreferencesRepository usersPreferencesRepository;

    // Ajustar a exceção
    public UsersPreferencesEntity atualizarPreferencias(UUID userId, CreateUserPreferencesDto dto) throws UsuarioNaoEncontradoException {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

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

    // Ajustar a exceção
    public UsersPreferencesEntity buscarPreferenciasDoUsuario(UUID userId) throws UsuarioNaoEncontradoException {
        return usersPreferencesRepository.findByUserId(userId)
            .orElseThrow(() -> new PreferenciasNaoEncontradasException("Este usuário não possui preferências cadastradas"));
    }
    
}
