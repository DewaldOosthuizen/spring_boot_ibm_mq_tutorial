package com.example.demo.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * The type Queue publisher.
 */
@Slf4j
@Service
public class JmsPublisher {

    /**
     * Publish event.
     *
     * @param topic       the topic
     * @param message     the message
     * @param jmsTemplate the jms template
     * @throws JMSException
     */
    public void publishEvent(String queue, String topic, TextMessage message, JmsTemplate jmsTemplate)
            throws JMSException {
        log.info(String.format(
                "Publishing to queue as TextMessage: \n %s\n :: published to %s on %s ::",
                message.getText(), queue, topic));
        jmsTemplate.convertAndSend(topic, message);
    }
}
