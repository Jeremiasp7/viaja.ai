package br.com.viajaai.controllers;

import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.dto.LoginRequestDto;
import br.com.planejaai.framework.dto.LoginResponseDto;
import br.com.planejaai.framework.service.BaseUserService;
import br.com.viajaai.services.ViagemAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final ViagemAuthService authService;
    private final BaseUserService userService;

    public AuthController(ViagemAuthService authService, BaseUserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(userService.criarUsuario(createUserDto));
    }
}