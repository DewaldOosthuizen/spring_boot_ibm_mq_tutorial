package com.example.demo.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The type Mq outgoing config.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.queues.queue2")
public class MQOutgoingConfig extends MQConfig {
    // maps all the outgoing queue settings
}
