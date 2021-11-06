package com.dots.crypto.service.arch;

import com.dots.crypto.service.command.MySubscriptionsCommand;
import com.dots.crypto.service.command.StartCommand;
import com.dots.crypto.service.command.SubscribeCommand;
import com.dots.crypto.service.command.UnsubscribeCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
@RequiredArgsConstructor
public enum CType {
    START("/start", StartCommand.class),
    SUBSCRIBE("/subscribe", SubscribeCommand.class),
    UNSUBSCRIBE("/unsubscribe", UnsubscribeCommand.class),
    MYSUBSCRIPTIONS("/mysubscriptions", MySubscriptionsCommand.class),
    UNDEFINED("", Void.class);

    private final String message;
    private final Class<?> clazz;

    public static String byClazz(Class<? extends Processor<?>> clazz) {
        return Arrays
                .stream(CType.values())
                .filter(t -> t.getClazz().isAssignableFrom(clazz))
                .findFirst()
                .map(CType::getMessage)
                .orElse(UNDEFINED.getMessage());
    }
}
