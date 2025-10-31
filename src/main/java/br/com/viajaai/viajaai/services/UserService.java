package br.com.viajaai.viajaai.services;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.viajaai.viajaai.dto.AuthenticationRequestDto;
import br.com.viajaai.viajaai.dto.CreateUserDto;
import br.com.viajaai.viajaai.dto.UpdateUserDto;
import br.com.viajaai.viajaai.entities.Role;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.exceptions.CriarUsuarioException;
import br.com.viajaai.viajaai.exceptions.SenhaIncorretaException;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
    // o @RequiredArgsConstructor cria o construtor automaticamente
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //private final UsersPreferencesRepository usersPreferencesRepository;
    
    public UserEntity criarUsuario(CreateUserDto dto) {
        try {
            UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .nome(dto.getNome())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .role(Role.USER)
                .build();

            return userRepository.save(user);

        } catch (Exception e) {
            throw new CriarUsuarioException("Erro no cadastro do usuário", e);
        }
    }

    public List<UserEntity> buscarUsuarios() {
        return userRepository.findAll();
    }

    public UserEntity buscarUsuarioPorId(UUID id) throws UsuarioNaoEncontradoException {
    return userRepository.findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
    }

    public UserEntity atualizarUsuario(UUID id, UpdateUserDto dto) throws UsuarioNaoEncontradoException {
        UserEntity user = buscarUsuarioPorId(id);

        if (dto.getNome() != null) user.setNome(dto.getNome());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());

        if (dto.getNovaSenha() == null || dto.getSenhaAntiga() == null || dto.getNovaSenha().isEmpty() || dto.getSenhaAntiga().isEmpty()) {
            throw new SenhaIncorretaException("Para alterar os dados, é necessário fornecer a senha antiga e a nova senha.");
        }

        if (dto.getSenhaAntiga() != null && dto.getNovaSenha() != null) {
            boolean senhaCorreta = passwordEncoder.matches(dto.getSenhaAntiga(), user.getSenha());

            if (!senhaCorreta) {
                throw new SenhaIncorretaException("Senha atual incorreta.");
            }

            String senhaCriptografada = passwordEncoder.encode(dto.getNovaSenha());
            user.setSenha(senhaCriptografada);
        }

        return userRepository.save(user);
    }

    public void deletarUsuario(UUID id) throws UsuarioNaoEncontradoException {
        UserEntity user = buscarUsuarioPorId(id);
        userRepository.delete(user);
    }

    public boolean isLoginCorrect(AuthenticationRequestDto loginRequest, UserEntity user) {
        return passwordEncoder.matches(loginRequest.getSenha(), user.getPassword());
    }

    
}
