package com.dots.crypto.service.arch;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class Processor<T> {
    private final ExecutorService executor;

    protected abstract Wrapper<T> before(final Update update,
                                         final TelegramLongPollingBot telegramBot,
                                         final Wrapper<T> result) throws Exception;

    protected abstract Wrapper<T> exec(final Update update,
                                       final TelegramLongPollingBot telegramBot,
                                       final Wrapper<T> result) throws Exception;

    protected abstract Wrapper<T> after(final Update update,
                                        final TelegramLongPollingBot telegramBot,
                                        final Wrapper<T> result) throws Exception;

    protected Processor() {
        final AtomicLong threadCounter = new AtomicLong(0);
        this.executor = Executors.newCachedThreadPool(r -> {
            final Thread x = new Thread(r);

            x.setDaemon(true);
            x.setPriority(Thread.MAX_PRIORITY);
            x.setName("processor_" + threadCounter.incrementAndGet());

            return x;
        });
    }

    public void executeSeq(final Update update,
                           final TelegramLongPollingBot telegramBot) {
        log.info("Processing command -> " + this.getClass().getSimpleName());

        Wrapper<T> result = initializeWrapper(update, telegramBot);

        try {
            result = this.before(update, telegramBot, result);
            result = this.exec(update, telegramBot, result);
            result = this.after(update, telegramBot, result);
        } catch (Throwable t) {
            result.setException(t);
        } finally {
            finalizeWrapper(result);
        }
    }

    public void executePar(final Update update,
                           final TelegramLongPollingBot telegramBot) {
        CompletableFuture.runAsync(
                () -> this.executeSeq(update, telegramBot),
                executor
        );
    }

    private Wrapper<T> initializeWrapper(final Update update,
                                         final TelegramLongPollingBot telegramBot) {
        final Wrapper<T> result = new Wrapper<>();

        result.setBotName(telegramBot.getClass().getSimpleName());
        result.setCommandName(this.getClass().getSimpleName());

        result.setStartTime(new Date());

        result.setCalledBy(update.getMessage().getFrom().getId());

        return result;
    }

    private void finalizeWrapper(final Wrapper<T> result) {
        result.setEndTime(new Date());
        result.setExecutionTime(result.getEndTime().getTime() - result.getStartTime().getTime());

        if (result.getException() == null) {
            log.info("Successfully processing command -> " + result.toString());
        } else {
            log.error("Error while processing command -> " + result.toString());
        }
    }
}
