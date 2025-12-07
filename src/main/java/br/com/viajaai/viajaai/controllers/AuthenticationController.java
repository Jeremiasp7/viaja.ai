package br.com.viajaai.viajaai.controllers;

import br.com.viajaai.viajaai.dto.AuthenticationRequestDto;
import br.com.viajaai.viajaai.dto.AuthenticationResponseDto;
import br.com.viajaai.viajaai.dto.CreateUserDto;
import br.com.viajaai.viajaai.entities.UserEntity;
import br.com.viajaai.viajaai.repositories.UserRepository;
import br.com.viajaai.viajaai.services.UserService;
import ch.qos.logback.core.util.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

  private final JwtEncoder jwtEncoder;
  private final UserRepository userRepository;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> login(
      @RequestBody AuthenticationRequestDto loginRequest) {

    var user = userRepository.findByEmail(loginRequest.getEmail());

    if (user.isEmpty() || !userService.isLoginCorrect(loginRequest, user.get())) {
      throw new BadCredentialsException("user or password is invalid!");
    }

    var now = Instant.now();
    var expiresIn = Duration.buildByDays(1).getMilliseconds();

    var claims =
        JwtClaimsSet.builder()
            .issuer("viajaai")
            .subject(user.get().getId().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .build();

    var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    return ResponseEntity.ok(new AuthenticationResponseDto(jwtValue, expiresIn));
  }

  @PostMapping("/register")
  public ResponseEntity<UserEntity> register(@RequestBody CreateUserDto dto) {
    var createdUser = userService.criarUsuario(dto);
    return ResponseEntity.ok(createdUser);
  }
}
