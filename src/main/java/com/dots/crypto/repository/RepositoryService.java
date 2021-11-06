package com.dots.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

public interface RepositoryService<T, ID> {

    default <R extends JpaRepository<T, ID>> T findOrSave(final ID id,
                                                          final R r,
                                                          final Supplier<T> supplier) {
        T entity;
        if (r.existsById(id)) {
            entity = r.getById(id);
        } else {
            entity = supplier.get();
            entity = r.save(entity);
        }
        return entity;
    }
}
