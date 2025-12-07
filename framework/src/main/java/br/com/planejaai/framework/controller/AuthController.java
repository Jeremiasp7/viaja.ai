package br.com.planejaai.framework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.dto.LoginRequestDto;
import br.com.planejaai.framework.dto.LoginResponseDto;
import br.com.planejaai.framework.entity.UserEntity;
import br.com.planejaai.framework.repository.UserRepository;
import br.com.planejaai.framework.service.AuthService;
import br.com.planejaai.framework.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("/register")
  public ResponseEntity<UserEntity> login(@RequestBody CreateUserDto createUserDto) {
    return ResponseEntity.ok(userService.criarUsuario(createUserDto));
  }
	
}
