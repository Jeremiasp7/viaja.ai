package br.com.viajaai.viajaai.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import br.com.viajaai.viajaai.dto.CreateUserDto;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.exceptions.UsuarioNaoEncontradoException;
import br.com.viajaai.viajaai.services.UserService;
import br.com.viajaai.viajaai.services.UserPreferencesService;

//import br.com.viajaai.viajaai.entities.UsersPreferencesEntity;
//import br.com.viajaai.viajaai.dto.CreateUserPreferencesDto;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    private final UserService service;

    public UserController(UserService service, UserPreferencesService preferencesService) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public UserEntity buscarPorId(@PathVariable UUID id) throws UsuarioNaoEncontradoException {
        return service.buscarUsuarioPorId(id);
    }

    @PutMapping("/{id}")
    public UserEntity atualizar(@PathVariable UUID id, @RequestBody CreateUserDto dto) throws UsuarioNaoEncontradoException {
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