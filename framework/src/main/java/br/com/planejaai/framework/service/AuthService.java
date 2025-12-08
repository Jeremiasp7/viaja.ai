package br.com.planejaai.framework.service;

import br.com.planejaai.framework.dto.LoginRequestDto;
import br.com.planejaai.framework.dto.LoginResponseDto;
import br.com.planejaai.framework.repository.BaseUserRepository;
import java.time.Duration;
import java.time.Instant;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

@AllArgsConstructor
public abstract class AuthService {

  private final JwtEncoder jwtEncoder;
  private final BaseUserRepository userRepository;
  private final BaseUserService userService;

  public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequest) {
    var user = userRepository.findByEmail(loginRequest.getEmail());

    if (user.isEmpty() || !userService.isLoginCorrect(loginRequest, user.get())) {
      throw new BadCredentialsException("user or password is invalid!");
    }

    var expiresIn = Duration.ofDays(1).toMillis();

    var now = Instant.now();
    var claims =
        JwtClaimsSet.builder()
            .issuer("viajaai")
            .subject(user.get().getId().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .build();

    var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    return ResponseEntity.ok(new LoginResponseDto(jwtValue, expiresIn));
  }
}
