package com.jobserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration for async job processing.
 * Uses a configurable thread pool for parallel job execution.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

    @Value("${job-server.async.core-pool-size:5}")
    private int corePoolSize;

    @Value("${job-server.async.max-pool-size:10}")
    private int maxPoolSize;

    @Value("${job-server.async.queue-capacity:100}")
    private int queueCapacity;

    @Value("${job-server.async.thread-name-prefix:job-processor-}")
    private String threadNamePrefix;

    /**
     * Creates a configurable task executor for async job processing.
     * Thread pool settings can be tuned via application properties.
     */
    @Bean(name = "jobProcessorExecutor")
    public Executor jobProcessorExecutor() {
        log.info("Configuring job processor executor: corePoolSize={}, maxPoolSize={}, queueCapacity={}",
                corePoolSize, maxPoolSize, queueCapacity);
        
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        
        return executor;
    }
}

