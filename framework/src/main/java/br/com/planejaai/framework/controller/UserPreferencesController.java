package br.com.planejaai.framework.controller;

import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import br.com.planejaai.framework.dto.GenericUsersPreferencesDto;
import br.com.planejaai.framework.entity.GenericUsersPreferencesEntity;
import br.com.planejaai.framework.exception.UsuarioNaoEncontradoException;
import br.com.planejaai.framework.service.interfaces.UserService; 
import br.com.planejaai.framework.service.interfaces.UserPreferencesService;

@RestController
@RequestMapping("/preferencias")
public class UserPreferencesController {
    private final UserPreferencesService preferencesService;

    public UserPreferencesController(UserService service, UserPreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }
    
    @GetMapping("/{userId}")
    public GenericUsersPreferencesEntity buscarPreferenciasDoUsuario(@PathVariable UUID userId) throws UsuarioNaoEncontradoException {
        return preferencesService.buscarPreferenciasDoUsuario(userId);
    }

    @PutMapping("/{userId}")
    public GenericUsersPreferencesEntity update(@PathVariable UUID userId, @RequestBody GenericUsersPreferencesDto preferencias) throws UsuarioNaoEncontradoException {
        return preferencesService.atualizarPreferencias(userId, preferencias);
    }

    @PostMapping("/{userId}")
    public GenericUsersPreferencesEntity create(@PathVariable UUID userId, @RequestBody GenericUsersPreferencesDto preferencias) throws UsuarioNaoEncontradoException {
        return preferencesService.atualizarPreferencias(userId, preferencias);
    }
}
