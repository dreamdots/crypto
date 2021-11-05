package com.dots.crypto.service.arch;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Wrapper<T> {
    private long executionTime;
    private Date startTime;
    private Date endTime;

    private int calledBy;

    private String botName;
    private String commandName;

    private T result;
    private Throwable exception;
}
