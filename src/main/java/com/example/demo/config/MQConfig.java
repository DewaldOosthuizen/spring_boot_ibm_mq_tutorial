package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The interface Imq config.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class MQConfig {
    private boolean disabled;
    private String host;
    private String queueManager;
    private String channel;
    private String password;
    private String userName;
    private String appName;
    private String appDescription;
    private String listenTopic;
    private String publishTopic;
    private String concurrent;
    private int port;
    private boolean pubSub;
}
