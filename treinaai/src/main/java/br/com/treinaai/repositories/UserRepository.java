package br.com.treinaai.repositories;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import br.com.planejaai.framework.repository.BaseUserRepository;

@Repository
@Primary
public interface UserRepository extends BaseUserRepository {}
