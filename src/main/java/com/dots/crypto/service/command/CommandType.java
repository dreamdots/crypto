package com.dots.crypto.service.command;

import com.dots.crypto.service.command.impl.MySubscriptionsCommand;
import com.dots.crypto.service.command.impl.StartCommand;
import com.dots.crypto.service.command.impl.SubscribeCommand;
import com.dots.crypto.service.command.impl.UnsubscribeCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
@RequiredArgsConstructor
public enum CommandType {
    START("/start", StartCommand.class),
    SUBSCRIBE("/subscribe", SubscribeCommand.class),
    UNSUBSCRIBE("/unsubscribe", UnsubscribeCommand.class),
    MYSUBSCRIPTIONS("/mysubscriptions", MySubscriptionsCommand.class),
    UNDEFINED("", Void.class);

    private final String message;
    private final Class<?> clazz;

    public static String byClazz(Class<? extends Processor<?>> clazz) {
        return Arrays
                .stream(CommandType.values())
                .filter(t -> t.getClazz().isAssignableFrom(clazz))
                .findFirst()
                .map(CommandType::getMessage)
                .orElse(UNDEFINED.getMessage());
    }
}
