package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;


/**
 * The type Mq outgoing factory.
 */
@Slf4j
@Configuration
public class MQOutgoingFactory {
    /**
     * The constant factory.
     */
    public static ConnectionFactory factory;

    /**
     * The Config.
     */
    @Autowired
    MQOutgoingConfig config;


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
    @Bean(name = "outgoingJmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() throws JMSException {
        factory = mqConnectionFactory.connectionFactory(factory, config);
        return mqConnectionFactory.defaultJmsListenerContainerFactory(factory, config);
    }


    /**
     * Outgoing jms template jms template.
     *
     * @return the jms template
     */
    @Bean(name = "outgoingJmsTemplate")
    public JmsTemplate outgoingJmsTemplate() throws JMSException {
        factory = mqConnectionFactory.connectionFactory(factory, config);
        JmsTemplate template = new JmsTemplate(factory);
        template.setPubSubDomain(config.isPubSub());
        return template;
    }


}
