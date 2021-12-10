package com.dots.crypto.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfiguration {

    @Bean(name = "processor_exec")
    public Executor processor() {
        return PoolUtils.constructCached("processor");
    }
}
