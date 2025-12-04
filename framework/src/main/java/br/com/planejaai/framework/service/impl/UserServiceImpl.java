package br.com.planejaai.framework.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.planejaai.framework.dto.AuthenticationRequestDto;
import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.dto.UpdateUserDto;
import br.com.planejaai.framework.entity.Role;
import br.com.planejaai.framework.entity.UserEntity;
import br.com.planejaai.framework.exception.CriarUsuarioException;
import br.com.planejaai.framework.exception.SenhaIncorretaException;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import br.com.planejaai.framework.repository.UserRepository;
import br.com.planejaai.framework.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserEntity criarUsuario(CreateUserDto dto) {
        try {
            UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .nome(dto.getNome())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .build();

            return userRepository.save(user);

        } catch (Exception e) {
            throw new CriarUsuarioException("Erro no cadastro do usuário", e);
        }
    }

    @Override
    public List<UserEntity> buscarUsuarios() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity buscarUsuarioPorId(UUID id) throws UsuarioNaoEncontradoException {
    return userRepository.findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
    }

    @Override
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

    @Override
    public void deletarUsuario(UUID id) throws UsuarioNaoEncontradoException {
        UserEntity user = buscarUsuarioPorId(id);
        userRepository.delete(user);
    }

    @Override
    public boolean isLoginCorrect(AuthenticationRequestDto loginRequest, UserEntity user) {
        return passwordEncoder.matches(loginRequest.getSenha(), user.getSenha());
    }
}
