package com.dots.crypto.service.router;

import com.dots.crypto.model.Process;
import com.dots.crypto.repository.ProcessRepository;
import com.dots.crypto.service.command.CommandType;
import com.dots.crypto.service.command.Processor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class Router {
    private final ProcessRepository processRepository;
    private final Map<Class<?>, Processor<?>> commands;

    @Async("processor_exec")
    public void execute(final Update update,
                        final TelegramLongPollingBot telegramBot) {
        log.info("Received update -> " + update.toString());

        final CommandType type = extractCommandType(update);
        log.info("Founded type -> " + type.toString());

        final Processor<?> processor = commands.get(type.getClazz());

        if (processor != null) {
            final Process<?> process = processor.execute(update, telegramBot);
            addToStatistics(process);
        } else {
            log.info("Processor -> " + type + " is nullable or disable");
        }
    }

    private CommandType extractCommandType(final Update update) {
        if (update.getMessage() != null) {
            String text = update.getMessage().getText();
            if (text != null) {
                text = text.toLowerCase().trim();

                for (final CommandType command : CommandType.values()) {
                    if (text.startsWith(command.getMessage())) {
                        return command;
                    }
                }
            }
        }

        return CommandType.UNDEFINED;
    }

    private void addToStatistics(final Process<?> process) {
        CompletableFuture.runAsync(() -> {
            processRepository.save(process);
        });
    }
}
