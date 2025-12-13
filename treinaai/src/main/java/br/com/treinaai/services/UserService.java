package br.com.treinaai.services;

import br.com.planejaai.framework.service.BaseUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseUserService {
  public UserService(
      br.com.planejaai.framework.repository.BaseUserRepository userRepository,
      org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
    super(userRepository, passwordEncoder);
  }
}
