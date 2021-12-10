package com.dots.crypto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "process")
public class Process<T extends BotApiMethod<Message>> {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "process_seq_gen")
    private Long id;

    @Column(name = "execution_time")
    private Long executionTime;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "called_by")
    private int calledBy;

    @Column(name = "bot_name")
    private String botName;
    @Column(name = "command_type")
    private String commandType;

    @Column(name = "is_successful")
    private Boolean isSuccessful;
    @Column(name = "exception_message")
    private String exceptionMessage;

    @Transient
    @JsonIgnore
    private transient T result;

    @Transient
    @JsonIgnore
    private transient Throwable exception;
}
