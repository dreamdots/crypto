package com.dots.crypto.configuration;

import com.dots.crypto.service.command.Processor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class CommandConfiguration {

    @Bean
    public Map<Class<?>, Processor<?>> commands(List<Processor<?>> all) {
        return all
                .stream()
                .peek(c -> log.info("Register command -> " + c.getClass().getSimpleName()))
                .collect(Collectors.toMap(
                        Processor::getClass,
                        Function.identity()
                ));
    }
}
