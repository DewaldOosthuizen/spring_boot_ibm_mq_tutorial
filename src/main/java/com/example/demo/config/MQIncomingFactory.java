package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;


/**
 * The type Mq incoming factory.
 */
@Slf4j
@Configuration
public class MQIncomingFactory {
    /**
     * The constant factory.
     */
    public static ConnectionFactory factory;

    /**
     * The Config.
     */
    @Autowired
    MQIncomingConfig config;

    /**
     * The Mq connection factory.
     */
    @Autowired
    MQConnectionFactory mqConnectionFactory;

    /**
     * Default jms listener container factory default jms listener container factory.
     *
     * @return the default jms listener container factory
     */
    @Bean(name = "incomingJmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
        factory = mqConnectionFactory.connectionFactory(factory, config);
        return mqConnectionFactory.defaultJmsListenerContainerFactory(factory, config);
    }

    /**
     * Incoming jms template jms template.
     *
     * @return the jms template
     */
    @Bean(name = "incomingJmsTemplate")
    public JmsTemplate incomingJmsTemplate() {
        factory = mqConnectionFactory.connectionFactory(factory, config);
        return new JmsTemplate(factory);
    }


}
