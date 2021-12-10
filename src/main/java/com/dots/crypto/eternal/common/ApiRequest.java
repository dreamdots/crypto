package com.dots.crypto.eternal.common;

import com.fasterxml.jackson.databind.JavaType;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public abstract class ApiRequest<R> {
    public abstract JavaType getType();
    public abstract RequestMethod getMethod();

    @SneakyThrows
    public ApiResponse<R> execute(final ApiClient client) {
        log.info("Execute request -> " + this.toString());
        //noinspection unchecked
        return client.execute(this);
    }
}
