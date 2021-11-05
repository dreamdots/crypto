package com.dots.crypto.service.response;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@UtilityClass
public class MessageBuilder {

    public static SendMessage message(final long chatId,
                                      final String text) {
        return SendMessage
                .builder()
                .chatId(String.valueOf(chatId))
                .text(text)
                .disableWebPagePreview(true)
                .disableNotification(false)
                .allowSendingWithoutReply(false)
                .build();
    }
}
