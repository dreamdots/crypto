package com.dots.crypto.configuration;

import lombok.experimental.UtilityClass;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@UtilityClass
public class PoolUtils {

    public static Executor constructCached(final String threadName) {
        return Executors.newCachedThreadPool(constructFactory(threadName));
    }

    public static Executor constructScheduler(final String threadName,
                                              final int poolSize) {
        return Executors.newScheduledThreadPool(poolSize, constructFactory(threadName));
    }

    private static ThreadFactory constructFactory(final String threadName) {
        final AtomicLong threadCounter = new AtomicLong(0);

        return r -> {
            final Thread x = new Thread(r);

            x.setDaemon(true);
            x.setPriority(Thread.MAX_PRIORITY);
            x.setName(threadName + "_" + threadCounter.incrementAndGet());

            return x;
        };
    }
}
