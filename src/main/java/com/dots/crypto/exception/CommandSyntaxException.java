package com.dots.crypto.exception;

import com.dots.crypto.service.command.CommandType;
import com.dots.crypto.service.command.Processor;

public class CommandSyntaxException extends RuntimeException {
    public CommandSyntaxException(Class<? extends Processor<?>> clazz) {
        super("Неверный синтаксис команды -> " + CommandType.byClazz(clazz));
    }
}
