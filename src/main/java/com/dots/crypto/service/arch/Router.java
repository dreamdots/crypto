package com.dots.crypto.service.arch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Router {
    private final Map<Class<?>, Processor<?>> commands;

    @Value("${telegram.asyncmode}")
    private boolean asyncMode;

    public void execute(final Update update,
                        final TelegramLongPollingBot telegramBot) {
        log.info("Received update -> " + update.toString());

        final CType type = extractCommandType(update);
        log.info("Founded type -> " + type.toString());

        final Processor<?> processor = commands.get(type.getClazz());

        if (processor != null) {
            if (asyncMode) {
                processor.executePar(update, telegramBot);
            } else {
                processor.executeSeq(update, telegramBot);
            }
        } else {
            log.info("Processor -> " + type + " is nullable or disable");
        }
    }

    private CType extractCommandType(final Update update) {
        if (update.getMessage() != null) {
            String text = update.getMessage().getText();
            if (text != null) {
                text = text.toLowerCase().trim();

                for (final CType command : CType.values()) {
                    if (command.getMessage().startsWith(text)) {
                        return command;
                    }
                }
            }
        }

        return CType.UNDEFINED;
    }
}
