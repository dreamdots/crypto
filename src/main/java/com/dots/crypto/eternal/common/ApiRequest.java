package com.dots.crypto.eternal.common;

import com.dots.crypto.eternal.bscscan.RequestAction;
import com.dots.crypto.eternal.bscscan.RequestModule;
import com.fasterxml.jackson.databind.JavaType;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public abstract class ApiRequest<T> {
    public abstract JavaType getType();
    public abstract RequestAction getAction();
    public abstract RequestModule getModule();
    public abstract RequestMethod getMethod();


    @SneakyThrows
    public ApiResponse<T> execute(final ApiClient client) {
        log.info("Execute request -> " + this.toString());
        return client.execute(this);
    }
}
