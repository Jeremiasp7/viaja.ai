package br.com.treinaai.services;

import org.springframework.stereotype.Service;

import br.com.planejaai.framework.service.AuthService;
import br.com.treinaai.repositories.UserRepository;

@Service
public class AuthenticationService extends AuthService {

  public AuthenticationService(
      org.springframework.security.oauth2.jwt.JwtEncoder jwtEncoder,
      UserRepository userRepository,
      UserService userService) {
    super(jwtEncoder, userRepository, userService);
  }
}
