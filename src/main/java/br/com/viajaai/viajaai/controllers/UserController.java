package br.com.viajaai.viajaai.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import br.com.viajaai.viajaai.dto.CreateUserDto;
import br.com.viajaai.viajaai.dto.CreateUserPreferencesDto;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
import br.com.viajaai.viajaai.services.UserService;
import br.com.viajaai.viajaai.services.UserPreferencesService;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    private final UserService service;
    private final UserPreferencesService preferencesService;

    public UserController(UserService service, UserPreferencesService preferencesService) {
        this.service = service;
        this.preferencesService = preferencesService;
    }

    @GetMapping("/{id}")
    public UserEntity buscarPorId(@PathVariable UUID id) {
        return service.buscarUsuarioPorId(id);
    }

    @PutMapping("/{id}")
    public UserEntity atualizar(@PathVariable UUID id, @RequestBody CreateUserDto dto) {
        return service.atualizarUsuario(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
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

    @GetMapping("/preferencias/{userId}")
    public UsersPreferencesEntity buscarPreferenciasDoUsuario(@PathVariable UUID userId) {
        return preferencesService.buscarPreferenciasDoUsuario(userId);
    }

    @PostMapping("preferencias/{userId}")
    public UsersPreferencesEntity create(@PathVariable UUID userId, @RequestBody CreateUserPreferencesDto preferencias) {
        return preferencesService.atualizarPreferencias(userId, preferencias);
    }
}