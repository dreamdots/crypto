package com.dots.crypto.service.arch;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProcessorWrapper<T extends BotApiMethod<Message>> {
    private long executionTime;
    private Date startTime;
    private Date endTime;

    private int calledBy;

    private String botName;
    private String commandName;

    private T result;
    private Throwable exception;
}
