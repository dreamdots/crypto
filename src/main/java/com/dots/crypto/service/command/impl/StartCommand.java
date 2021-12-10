package com.dots.crypto.service.command.impl;

import com.dots.crypto.model.Process;
import com.dots.crypto.service.command.ProcessorWithoutHooks;
import com.dots.crypto.service.response.MessageBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@ConditionalOnProperty(value = "telegram.commands.start")
public class StartCommand extends ProcessorWithoutHooks<SendMessage> {
    private final static String RESPONSE_MSG = "Привет!\n" +
            "Доступные команды: \n" +
            "/subscribe tokenContract transactionFunds - подписка на уведомления на транзакции большей суммы чем (transactionFunds) для токена (tokenContract)\n" +
            "/unsubscribe tokenContract - отмена уведомлений по действующей подписке\n" +
            "/mysubscriptions - список всех подписок";

    @Override
    protected Process<SendMessage> exec(final Update update,
                                        final TelegramLongPollingBot telegramBot,
                                        final Process<SendMessage> result)  throws Exception {
        result.setResult(MessageBuilder.message(update.getMessage().getChatId(), RESPONSE_MSG));
        return result;
    }
}
