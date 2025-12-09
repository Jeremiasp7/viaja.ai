package br.com.estudaai.service;

import br.com.estudaai.repository.UserRepository;
import br.com.planejaai.framework.service.BaseUserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserService extends BaseUserService {
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    super(userRepository, passwordEncoder);
  }
}
