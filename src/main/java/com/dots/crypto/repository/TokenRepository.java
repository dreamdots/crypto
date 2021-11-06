package com.dots.crypto.repository;

import com.dots.crypto.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String>, RepositoryService<Token, String> { }
