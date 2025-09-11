package br.com.viajaai.viajaai.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import br.com.viajaai.viajaai.dto.CreateUserDto;
import br.com.viajaai.viajaai.entities.UserEntity;
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
}