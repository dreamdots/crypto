package com.dots.crypto.eternal.bscscan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestAction {
    TXLIST("txlist"),
    TOKENNFTTX("tokennfttx");

    private final String action;
}
