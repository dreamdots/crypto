package com.dots.crypto.service.command.impl;

import com.dots.crypto.model.Process;
import com.dots.crypto.service.command.ProcessorWithoutHooks;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@ConditionalOnProperty(value = "telegram.commands.mysubscriptions")
public class MySubscriptionsCommand extends ProcessorWithoutHooks<SendMessage> {

    @Override
    protected Process<SendMessage> exec(final Update update,
                                        final TelegramLongPollingBot telegramBot,
                                        final Process<SendMessage> result) throws Exception {
        return null;
    }
}
