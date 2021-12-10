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
@ConditionalOnProperty(value = "telegram.commands.subscribe")
public class SubscribeCommand extends ProcessorWithoutHooks<SendMessage> {
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
        if (args.length != 3) {
            throw new CommandSyntaxException(this.getClass());
        }

        final long threshold = Long.parseLong(args[2]);
        final String contract = args[1];

        final TelegramUser telegramUser = telegramUserRepository.findOrSave(
                message.getChatId(),
                telegramUserRepository,
                () -> {
                    final TelegramUser u = new TelegramUser();
                    u.setChatId(message.getChatId());
                    u.setUserId(update.getMessage().getFrom().getId());
                    return u;
                }
        );

        if (subscriptionRepository.existsByTelegramUser_ChatIdAndToken_Contract(message.getChatId(), contract)) {
            throw new CommandLogicException("Вы уже подписаны на обновления токена -> " + contract);
        }

        final Token token = tokenRepository.findOrSave(
                contract,
                tokenRepository,
                () -> {
                    final Token tk = new Token();
                    tk.setContract(contract);
                    return tk;
                });

        Subscription subscription = new Subscription();
        subscription.setThreshold(threshold);
        subscription.setToken(token);
        subscription.setTelegramUser(telegramUser);
        subscription = subscriptionRepository.save(subscription);

        telegramUserRepository.addNewSubscription(message.getChatId(), subscription.getId());

        result.setResult(MessageBuilder.message(update.getMessage().getChatId(), DEFAULT_RESPONSE));
        return result;
    }
}
