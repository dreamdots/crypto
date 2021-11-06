package com.dots.crypto.service.arch;

import com.dots.crypto.service.response.MessageBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;

@Slf4j
public abstract class Processor<T extends BotApiMethod<Message>> {
    protected final String DEFAULT_RESPONSE = "Успешно";

    protected abstract ProcessorWrapper<T> before(final Update update,
                                                  final TelegramLongPollingBot telegramBot,
                                                  final ProcessorWrapper<T> result) throws Exception;

    protected abstract ProcessorWrapper<T> exec(final Update update,
                                                final TelegramLongPollingBot telegramBot,
                                                final ProcessorWrapper<T> result) throws Exception;

    protected abstract ProcessorWrapper<T> after(final Update update,
                                                 final TelegramLongPollingBot telegramBot,
                                                 final ProcessorWrapper<T> result) throws Exception;

    protected final String[] extractArgs(final String text) {
        return text.trim().split(" ");
    }

    @SneakyThrows
    public final void execute(final Update update,
                        final TelegramLongPollingBot telegramBot) {
        ProcessorWrapper<T> result = initializeWrapper(update, telegramBot);

        log.info("Run command -> " + result.toString());

        try {
            result = this.before(update, telegramBot, result);
            result = this.exec(update, telegramBot, result);
            result = this.after(update, telegramBot, result);

            telegramBot.execute(result.getResult());

        } catch (Throwable t) {
            result.setException(t);
            telegramBot.execute(MessageBuilder.message(update.getMessage().getChatId(), (t.getMessage() == null
                    ? "Произошла непредвиденная ошибка, попробуйте позже!"
                    : t.getMessage())));
        } finally {
            finalizeWrapper(result);
        }
    }

    private ProcessorWrapper<T> initializeWrapper(final Update update,
                                                  final TelegramLongPollingBot telegramBot) {
        final ProcessorWrapper<T> result = new ProcessorWrapper<>();

        result.setBotName(telegramBot.getClass().getSimpleName());
        result.setCommandName(this.getClass().getSimpleName());
        result.setStartTime(new Date());
        result.setCalledBy(update.getMessage().getFrom().getId());

        return result;
    }

    private void finalizeWrapper(final ProcessorWrapper<T> result) {
        result.setEndTime(new Date());
        result.setExecutionTime(result.getEndTime().getTime() - result.getStartTime().getTime());

        if (result.getException() == null) {
            log.info("Successfully processing command -> " + result.toString());
        } else {
            if (result.getException() instanceof RuntimeException) {
                log.warn("Error while processing command -> " + result.toString(), result.getException());
            } else {
                log.error("Error while processing command -> " + result.toString(), result.getException());
            }
        }
    }
}
