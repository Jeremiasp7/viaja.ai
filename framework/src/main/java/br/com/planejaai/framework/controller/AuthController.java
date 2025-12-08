package br.com.planejaai.framework.controller;

import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.dto.LoginRequestDto;
import br.com.planejaai.framework.dto.LoginResponseDto;
import br.com.planejaai.framework.entity.UserEntity;
import br.com.planejaai.framework.service.AuthService;
import br.com.planejaai.framework.service.BaseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final BaseUserService userService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("/register")
  public ResponseEntity<UserEntity> register(@RequestBody CreateUserDto createUserDto) {
    return ResponseEntity.ok(userService.criarUsuario(createUserDto));
  }
}
