package com.dots.crypto.repository;

import com.dots.crypto.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer>, RepositoryService<RoleEntity, Integer>{
}
