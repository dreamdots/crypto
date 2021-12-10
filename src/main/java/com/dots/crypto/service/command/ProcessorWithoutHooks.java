package com.dots.crypto.service.command;

import com.dots.crypto.model.Process;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class ProcessorWithoutHooks<T extends BotApiMethod<Message>> extends Processor<T> {

    @Override
    protected final Process<T> before(final Update update,
                                      final TelegramLongPollingBot telegramBot,
                                      final Process<T> result)  throws Exception {
        return result;
    }

    @Override
    protected final Process<T> after(final Update update,
                                     final TelegramLongPollingBot telegramBot,
                                     final Process<T> result)  throws Exception {
        return result;
    }
}
