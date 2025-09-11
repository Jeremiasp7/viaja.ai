package br.com.viajaai.viajaai.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.viajaai.viajaai.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{
    
}
