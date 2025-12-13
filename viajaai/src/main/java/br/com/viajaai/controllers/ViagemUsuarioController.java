package br.com.viajaai.controllers;

import br.com.planejaai.framework.controller.BaseUserController;
import br.com.planejaai.framework.dto.UpdateUserDto;
import br.com.planejaai.framework.entity.BaseUserEntity;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import br.com.planejaai.framework.service.BaseUserService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viagens/users")
public class ViagemUsuarioController extends BaseUserController {

  public ViagemUsuarioController(BaseUserService service) {
    super(service);
  }


  @Override
  @GetMapping("/{id}")
  public BaseUserEntity buscarPorId(@PathVariable("id") UUID id) throws UsuarioNaoEncontradoException {
    return super.buscarPorId(id);
  }

  @Override
  @PutMapping("/{id}")
  public BaseUserEntity atualizar(@PathVariable("id") UUID id, @Valid @RequestBody UpdateUserDto dto)
      throws UsuarioNaoEncontradoException {
    return super.atualizar(id, dto);
  }

  @Override
  @DeleteMapping("/{id}")
  public void deletar(@PathVariable("id") UUID id) throws UsuarioNaoEncontradoException {
    super.deletar(id);
  }
}