package br.com.planejaai.framework.controller;

import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.dto.UpdateUserDto;
import br.com.planejaai.framework.entity.UserEntity;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import br.com.planejaai.framework.service.BaseUserService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public abstract class BaseUserController {
  private final BaseUserService service;

  public BaseUserController(BaseUserService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public UserEntity buscarPorId(@PathVariable UUID id) throws UsuarioNaoEncontradoException {
    return service.buscarUsuarioPorId(id);
  }

  @PutMapping("/{id}")
  public UserEntity atualizar(@PathVariable UUID id, @RequestBody UpdateUserDto dto)
      throws UsuarioNaoEncontradoException {
    return service.atualizarUsuario(id, dto);
  }

  @DeleteMapping("/{id}")
  public void deletar(@PathVariable UUID id) throws UsuarioNaoEncontradoException {
    service.deletarUsuario(id);
  }

  @GetMapping
  public List<UserEntity> listAll() {
    return service.buscarUsuarios();
  }

  @PostMapping
  public UserEntity create(@RequestBody CreateUserDto user) {
    return service.criarUsuario(user);
  }
}
