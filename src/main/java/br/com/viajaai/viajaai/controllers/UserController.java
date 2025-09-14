package br.com.viajaai.viajaai.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import br.com.viajaai.viajaai.dto.CreateUserDto;
import br.com.viajaai.viajaai.dto.CreateUserPreferencesDto;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.services.UserService;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserEntity> listAll() {
        return service.buscarUsuarios();
    }

    @PostMapping
    public UserEntity create(@RequestBody CreateUserDto user) {
        return service.criarUsuario(user);
    }

    @GetMapping("/preferencias/{userId}")
    public UsersPreferencesEntity buscarPreferenciasDoUsuario(@PathVariable UUID userId) {
        return service.buscarPreferenciasDoUsuario(userId);
    }

    @PostMapping("/preferencias/{userId}")
    public UsersPreferencesEntity create(@PathVariable UUID userId, @RequestBody CreateUserPreferencesDto preferencias) {
        return service.atualizarPreferencias(userId, preferencias);
    }
}
