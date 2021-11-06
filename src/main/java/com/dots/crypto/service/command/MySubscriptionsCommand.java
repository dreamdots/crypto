package com.dots.crypto.service.command;

import com.dots.crypto.service.arch.ProcessorWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@ConditionalOnProperty(value = "telegram.commands.mysubscriptions")
public class MySubscriptionsCommand extends ProcessorWithoutHooks<SendMessage> {

    @Override
    protected ProcessorWrapper<SendMessage> exec(final Update update,
                                                 final TelegramLongPollingBot telegramBot,
                                                 final ProcessorWrapper<SendMessage> result) throws Exception {
        return null;
    }
}
