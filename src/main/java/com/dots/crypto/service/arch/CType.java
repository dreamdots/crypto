package com.dots.crypto.service.arch;

import com.dots.crypto.service.command.StartCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum CType {
    START("/start", StartCommand.class),
    UNDEFINED("", Void.class);

    private final String message;
    private final Class<?> clazz;
}
