package br.com.estudaai.service;

import br.com.estudaai.entity.StudyUser;
import br.com.estudaai.repository.UserRepository;
import br.com.planejaai.framework.dto.CreateUserDto;
import br.com.planejaai.framework.service.BaseUserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserService extends BaseUserService<StudyUser> {
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    super(userRepository, passwordEncoder);
  }

  @Override
  public StudyUser criarUsuario(CreateUserDto dto) {
    StudyUser user =
        StudyUser.builder()
            .nome(dto.getNome())
            .email(dto.getEmail())
            .senha(passwordEncoder.encode(dto.getSenha()))
            .build();

    return userRepository.save(user);
  }
}
