package com.backendless.test.core.utils;

import com.backendless.test.core.exceptions.CleanupTaskException;
import com.backendless.test.core.pool.EntityPool;
import com.backendless.test.core.service.RemoteService;
import com.backendless.test.core.service.SettingsHolder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Component
@Scope("singleton")
public class CleanupExecutor {
    private static final String TASK_EXECUTOR = "Cleanup.Executor";
    private final ScheduledExecutorService scheduledExecutor;
    private final EntityPool entityPool;
    private final RemoteService remoteService;
    private final SettingsHolder settingsHolder;

    private ScheduledFuture cleanupFuture = null;


    public CleanupExecutor(EntityPool entityPool,
                           RemoteService remoteService,
                           SettingsHolder settingsHolder) {
        this.entityPool = entityPool;
        this.remoteService = remoteService;
        this.settingsHolder = settingsHolder;
        this.scheduledExecutor = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder()
                .setNameFormat(TASK_EXECUTOR + "ScheduledTask-%d")
                .setDaemon(true)
                .setUncaughtExceptionHandler((t, e) -> new CleanupTaskException(e.getMessage()))
                .build());

    }

    @PostConstruct
    private void init() {
        initScheduledTask();
    }

    @PreDestroy
    private void destroy() throws InterruptedException {
        scheduledExecutor.shutdown();
        scheduledExecutor.awaitTermination(10, SECONDS);
    }

    private void initScheduledTask() {
        scheduledExecutor.scheduleWithFixedDelay(() -> {
            entityPool.values().forEach(list -> list.removeIf((entityWrapper) -> {
                if (System.currentTimeMillis() - entityWrapper.getModified() <
                        settingsHolder.getSettings().getCleanUpTimeUnit().toMillis(settingsHolder.getSettings().getCleanUpTimePeriod())) {
                    remoteService.deleteById(entityWrapper.getEntity().getClass(), entityWrapper.getRef());
                    return true;
                }
                return false;
            }));
        }, 0, 5, TimeUnit.SECONDS);
    }
}
