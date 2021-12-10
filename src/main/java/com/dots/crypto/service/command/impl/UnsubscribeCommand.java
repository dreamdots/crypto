package com.dots.crypto.service.command.impl;

import com.dots.crypto.exception.CommandLogicException;
import com.dots.crypto.exception.CommandSyntaxException;
import com.dots.crypto.model.Process;
import com.dots.crypto.model.Subscription;
import com.dots.crypto.model.TelegramUser;
import com.dots.crypto.model.Token;
import com.dots.crypto.repository.SubscriptionRepository;
import com.dots.crypto.repository.TelegramUserRepository;
import com.dots.crypto.repository.TokenRepository;
import com.dots.crypto.service.command.ProcessorWithoutHooks;
import com.dots.crypto.service.response.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "telegram.commands.unsubscribe")
public class UnsubscribeCommand extends ProcessorWithoutHooks<SendMessage> {
    private final TelegramUserRepository telegramUserRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    protected Process<SendMessage> exec(final Update update,
                                        final TelegramLongPollingBot telegramBot,
                                        final Process<SendMessage> result) throws Exception {
        final Message message = update.getMessage();

        final String[] args = extractArgs(message.getText());
        if (args.length != 2) {
            throw new CommandSyntaxException(this.getClass());
        }

        final String contract = args[1];
        final long chatId = message.getChatId();

        final TelegramUser telegramUser = telegramUserRepository.findById(chatId).orElse(null);
        if (telegramUser == null) {
            throw new CommandLogicException("Вы не подписаны на какие-либо обновления");
        }

        final Token token = tokenRepository.findById(contract).orElse(null);
        if (token == null) {
            throw new CommandLogicException("Вы не подписаны на обновления токена -> " + contract);
        }

        final Subscription subscription = subscriptionRepository.findByTelegramUser_ChatIdAndToken_Contract(chatId, contract).orElse(null);
        if (subscription == null) {
            throw new CommandLogicException("Вы не подписаны на обновления токена -> " + contract);
        }

        telegramUserRepository.removeSubscription(chatId, subscription.getId());

        subscriptionRepository.delete(subscription);

        result.setResult(MessageBuilder.message(update.getMessage().getChatId(), DEFAULT_RESPONSE));
        return result;
    }
}
