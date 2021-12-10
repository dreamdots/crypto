package com.dots.crypto.repository;

import com.dots.crypto.model.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface APIUserRepository extends JpaRepository<APIUser, Long>, RepositoryService<APIUser, Long>{
    Optional<APIUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
