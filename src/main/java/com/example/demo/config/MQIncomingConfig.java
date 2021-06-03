package com.example.demo.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The type Mq incoming config.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.queues.queue1")
public class MQIncomingConfig extends MQConfig {
    // maps all the incoming queue settings
}
