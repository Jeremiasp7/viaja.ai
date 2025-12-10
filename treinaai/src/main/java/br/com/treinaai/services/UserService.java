package br.com.treinaai.services;

import org.springframework.stereotype.Service;

import br.com.planejaai.framework.service.BaseUserService;

@Service
public class UserService extends BaseUserService {
  public UserService(
      br.com.planejaai.framework.repository.BaseUserRepository userRepository,
      org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
    super(userRepository, passwordEncoder);
  }
}
