package br.com.estudaai.service;

import br.com.planejaai.framework.repository.UserRepository;
import br.com.planejaai.framework.service.AuthService;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService extends AuthService {
  public AuthenticationService(
      JwtEncoder jwtEncoder, UserRepository userRepository, UserService userService) {
    super(jwtEncoder, userRepository, userService);
  }
}
