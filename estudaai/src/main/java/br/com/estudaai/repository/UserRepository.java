package br.com.estudaai.repository;

import br.com.estudaai.entity.StudyUser;
import br.com.planejaai.framework.repository.BaseUserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface UserRepository extends BaseUserRepository<StudyUser> {}
