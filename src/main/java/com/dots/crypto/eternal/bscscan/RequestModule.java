package com.dots.crypto.eternal.bscscan;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestModule {
    ACCOUNT("account");

    private final String param;
}
