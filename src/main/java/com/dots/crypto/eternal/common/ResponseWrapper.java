package com.dots.crypto.eternal.common;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {
    private int status;
    private String message;
    private T result;

    public boolean isSuccess() {
        return this.status != 0;
    }

    public T or(T other) {
        if (isSuccess()) return this.getResult();
        else return other;
    }
}
