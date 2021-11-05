package com.dots.crypto.service.command;

import com.dots.crypto.service.arch.Processor;
import com.dots.crypto.service.arch.Wrapper;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class ProcessorWithoutHooks<T> extends Processor<T> {

    @Override
    protected final Wrapper<T> before(final Update update,
                                      final TelegramLongPollingBot telegramBot,
                                      final Wrapper<T> result)  throws Exception {
        return result;
    }

    @Override
    protected final Wrapper<T> after(final Update update,
                                     final TelegramLongPollingBot telegramBot,
                                     final Wrapper<T> result)  throws Exception {
        return result;
    }
}
