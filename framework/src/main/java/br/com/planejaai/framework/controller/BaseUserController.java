package br.com.planejaai.framework.controller;

import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.dto.UpdateUserDto;
import br.com.planejaai.framework.entity.BaseUserEntity;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import br.com.planejaai.framework.service.BaseUserService;
import jakarta.validation.Valid;
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
  public BaseUserEntity buscarPorId(@PathVariable UUID id) throws UsuarioNaoEncontradoException {
    return service.buscarUsuarioPorId(id);
  }

  @PutMapping("/{id}")
  public BaseUserEntity atualizar(@PathVariable UUID id, @Valid @RequestBody UpdateUserDto dto)
      throws UsuarioNaoEncontradoException {
    return service.atualizarUsuario(id, dto);
  }

  @DeleteMapping("/{id}")
  public void deletar(@PathVariable UUID id) throws UsuarioNaoEncontradoException {
    service.deletarUsuario(id);
  }

  @GetMapping
  public List<BaseUserEntity> listAll() {
    return service.buscarUsuarios();
  }

  @PostMapping
  public BaseUserEntity create(@Valid @RequestBody CreateUserDto user) {
    return service.criarUsuario(user);
  }
}
