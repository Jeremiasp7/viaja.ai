package br.com.treinaai.controllers;

import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.dto.LoginRequestDto;
import br.com.planejaai.framework.dto.LoginResponseDto;
import br.com.treinaai.services.AuthenticationService;
import br.com.treinaai.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TrainAuthController {

  private final AuthenticationService authenticationService;
  private final UserService userService;

  public TrainAuthController(AuthenticationService authenticationService, UserService userService) {
    this.authenticationService = authenticationService;
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody CreateUserDto createUserDto) {
    var user = userService.criarUsuario(createUserDto);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
    return authenticationService.login(loginRequestDto);
  }
}
