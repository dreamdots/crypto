package com.dots.crypto.eternal.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestMethod {
    GET("GET");

    private final String method;
}
