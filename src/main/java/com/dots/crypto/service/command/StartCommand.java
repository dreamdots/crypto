package com.dots.crypto.service.command;

import com.dots.crypto.service.arch.Wrapper;
import com.dots.crypto.service.response.MessageBuilder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand extends ProcessorWithoutHooks<SendMessage> {
    private final static String HELLO_MESSAGE = "Hello!";

    @Override
    protected Wrapper<SendMessage> exec(final Update update,
                                        final TelegramLongPollingBot telegramBot,
                                        final Wrapper<SendMessage> result)  throws Exception {
        final SendMessage msg = MessageBuilder.message(update.getMessage().getChatId(), HELLO_MESSAGE);
        result.setResult(msg);

        telegramBot.execute(msg);

        return result;
    }
}
