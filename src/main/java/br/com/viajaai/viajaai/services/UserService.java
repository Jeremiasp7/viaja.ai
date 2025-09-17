package br.com.viajaai.viajaai.services;

import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.CreateUserDto;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

//import br.com.viajaai.viajaai.dto.CreateUserPreferencesDto;
//import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
//import br.com.viajaai.viajaai.repositories.UsersPreferencesRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    // o @RequiredArgsConstructor cria o construtor automaticamente
    private final UserRepository userRepository;
    //private final UsersPreferencesRepository usersPreferencesRepository;
    
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

    // Ajustar a exceção
    public UserEntity buscarUsuarioPorId(UUID id) throws UsuarioNaoEncontradoException {
    return userRepository.findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
    }

    public UserEntity atualizarUsuario(UUID id, CreateUserDto dto) throws UsuarioNaoEncontradoException {
        UserEntity user = buscarUsuarioPorId(id);
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        return userRepository.save(user);
    }

    public void deletarUsuario(UUID id) throws UsuarioNaoEncontradoException {
        UserEntity user = buscarUsuarioPorId(id);
        userRepository.delete(user);
    }

    
}
