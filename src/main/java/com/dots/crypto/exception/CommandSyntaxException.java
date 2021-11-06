package com.dots.crypto.exception;

import com.dots.crypto.service.arch.CType;
import com.dots.crypto.service.arch.Processor;

public class CommandSyntaxException extends RuntimeException {
    public CommandSyntaxException(Class<? extends Processor<?>> clazz) {
        super("Неверный синтаксис команды -> " + CType.byClazz(clazz));
    }
}
