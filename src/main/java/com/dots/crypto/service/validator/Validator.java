package com.dots.crypto.service.validator;

public interface Validator<T, R> {
    void validatePojo(final T pojo) throws Exception;
    void validateEntity(final R entity) throws Exception;
}
