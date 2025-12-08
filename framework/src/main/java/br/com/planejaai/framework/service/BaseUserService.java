package br.com.planejaai.framework.service;

import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.dto.LoginRequestDto;
import br.com.planejaai.framework.dto.UpdateUserDto;
import br.com.planejaai.framework.entity.BaseUserEntity;
import br.com.planejaai.framework.exception.CriarUsuarioException;
import br.com.planejaai.framework.exception.SenhaIncorretaException;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import br.com.planejaai.framework.repository.BaseUserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseUserService {
  private final BaseUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public BaseUserEntity criarUsuario(CreateUserDto dto) {
    try {
      BaseUserEntity user =
          BaseUserEntity.builder()
              .email(dto.getEmail())
              .nome(dto.getNome())
              .senha(passwordEncoder.encode(dto.getSenha()))
              .build();

      return userRepository.save(user);

    } catch (Exception e) {

      throw new CriarUsuarioException(dto.toString(), e);
    }
  }

  public List<BaseUserEntity> buscarUsuarios() {
    return userRepository.findAll();
  }

  public BaseUserEntity buscarUsuarioPorId(UUID id) throws UsuarioNaoEncontradoException {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
  }

  public BaseUserEntity atualizarUsuario(UUID id, UpdateUserDto dto)
      throws UsuarioNaoEncontradoException {
    BaseUserEntity user = buscarUsuarioPorId(id);

    if (dto.getNome() != null) user.setNome(dto.getNome());
    if (dto.getEmail() != null) user.setEmail(dto.getEmail());

    if (dto.getNovaSenha() == null
        || dto.getSenhaAntiga() == null
        || dto.getNovaSenha().isEmpty()
        || dto.getSenhaAntiga().isEmpty()) {
      throw new SenhaIncorretaException(
          "Para alterar os dados, é necessário fornecer a senha antiga e a nova senha.");
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
    BaseUserEntity user = buscarUsuarioPorId(id);
    userRepository.delete(user);
  }

  public boolean isLoginCorrect(LoginRequestDto loginRequest, BaseUserEntity user) {
    return passwordEncoder.matches(loginRequest.getSenha(), user.getPassword());
  }
}
