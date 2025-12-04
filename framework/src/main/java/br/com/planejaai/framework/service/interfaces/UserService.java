package br.com.planejaai.framework.service.interfaces;

import br.com.planejaai.framework.dto.AuthenticationRequestDto;
import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.dto.UpdateUserDto;
import br.com.planejaai.framework.entity.UserEntity;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserEntity criarUsuario(CreateUserDto dto);

    List<UserEntity> buscarUsuarios();

    UserEntity buscarUsuarioPorId(UUID id) throws UsuarioNaoEncontradoException;

    UserEntity atualizarUsuario(UUID id, UpdateUserDto dto) throws UsuarioNaoEncontradoException;

    void deletarUsuario(UUID id) throws UsuarioNaoEncontradoException;

    boolean isLoginCorrect(AuthenticationRequestDto loginRequest, UserEntity user);
}
