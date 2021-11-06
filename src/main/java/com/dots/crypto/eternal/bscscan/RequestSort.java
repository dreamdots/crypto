package com.dots.crypto.eternal.bscscan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestSort {
    ASC("asc"),
    DESC("desc");

    private final String sort;
}
