package br.com.viajaai.viajaai.controllers;

import br.com.viajaai.viajaai.dto.CreateUserPreferencesDto;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.services.UserPreferencesService;
import br.com.viajaai.viajaai.services.UserService;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferencias")
public class UserPreferencesController {
  private final UserPreferencesService preferencesService;

  public UserPreferencesController(UserService service, UserPreferencesService preferencesService) {
    this.preferencesService = preferencesService;
  }

  @GetMapping("/{userId}")
  public UsersPreferencesEntity buscarPreferenciasDoUsuario(@PathVariable UUID userId)
      throws UsuarioNaoEncontradoException {
    return preferencesService.buscarPreferenciasDoUsuario(userId);
  }

  @PutMapping("/{userId}")
  public UsersPreferencesEntity update(
      @PathVariable UUID userId, @RequestBody CreateUserPreferencesDto preferencias)
      throws UsuarioNaoEncontradoException {
    return preferencesService.atualizarPreferencias(userId, preferencias);
  }

  @PostMapping("/{userId}")
  public UsersPreferencesEntity create(
      @PathVariable UUID userId, @RequestBody CreateUserPreferencesDto preferencias)
      throws UsuarioNaoEncontradoException {
    return preferencesService.atualizarPreferencias(userId, preferencias);
  }
}
