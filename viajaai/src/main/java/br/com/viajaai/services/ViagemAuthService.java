package br.com.viajaai.services;

import br.com.planejaai.framework.repository.BaseUserRepository;
import br.com.planejaai.framework.service.AuthService;
import br.com.planejaai.framework.service.BaseUserService;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

@Service
public class ViagemAuthService extends AuthService {

  public ViagemAuthService(
      JwtEncoder jwtEncoder, 
      BaseUserRepository userRepository, 
      BaseUserService userService) {
    super(jwtEncoder, userRepository, userService);
  }
  
}