package com.dots.crypto.service.command;

import com.dots.crypto.model.Process;
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

    protected abstract Process<T> before(final Update update,
                                         final TelegramLongPollingBot telegramBot,
                                         final Process<T> result) throws Exception;

    protected abstract Process<T> exec(final Update update,
                                       final TelegramLongPollingBot telegramBot,
                                       final Process<T> result) throws Exception;

    protected abstract Process<T> after(final Update update,
                                        final TelegramLongPollingBot telegramBot,
                                        final Process<T> result) throws Exception;

    protected final String[] extractArgs(final String text) {
        return text.trim().split(" ");
    }

    @SneakyThrows
    public final Process<T> execute(final Update update,
                                    final TelegramLongPollingBot telegramBot) {
        Process<T> result = initialize(update, telegramBot);

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
        }

        return finalize(result);
    }

    private Process<T> initialize(final Update update,
                                  final TelegramLongPollingBot telegramBot) {
        final Process<T> result = new Process<>();

        result.setBotName(telegramBot.getClass().getSimpleName());
        result.setCommandType(this.getClass().getSimpleName());
        result.setStartTime(new Date());
        result.setCalledBy(update.getMessage().getFrom().getId());

        return result;
    }

    private Process<T> finalize(final Process<T> result) {
        result.setEndTime(new Date());
        result.setExecutionTime(result.getEndTime().getTime() - result.getStartTime().getTime());

        final Throwable exception = result.getException();

        result.setException(exception);
        if (exception != null) {
            result.setExceptionMessage(exception.getMessage());
            result.setIsSuccessful(false);
        } else {
            result.setIsSuccessful(true);
        }

        return result;
    }
}
