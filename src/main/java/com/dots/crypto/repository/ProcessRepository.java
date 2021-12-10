package com.dots.crypto.repository;

import com.dots.crypto.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends JpaRepository<Process<?>, Integer>, RepositoryService<Process<?>, Integer>{}
