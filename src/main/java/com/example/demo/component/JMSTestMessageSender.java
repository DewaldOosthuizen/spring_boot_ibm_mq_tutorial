package com.example.demo.component;

import lombok.extern.slf4j.Slf4j;
import com.example.demo.config.MQIncomingFactory;

import com.example.demo.utils.JmsMessageCreator;
import com.example.demo.domain.Header;
import com.example.demo.config.MQIncomingConfig;
import com.example.demo.domain.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.UUID;
import javax.jms.JMSException;

import com.example.demo.service.JmsPublisher;

/**
 * The type JMS Test Message sender.
 * <p>
 * Only enable if mock is enabled Should be used for TESTING purposes only!!!!!
 */
@Slf4j
@Component
@ConditionalOnExpression(value = "${spring.queues.queue1.mockit}")
public class JMSTestMessageSender {

        /**
         * The Queue publisher.
         */
        @Autowired
        JmsPublisher JMSPublisher;

        /**
         * Gets the incoming event queue configs
         */
        @Autowired
        private MQIncomingConfig config;

        /**
         * The Jms template.
         */
        @Autowired
        @Qualifier("incomingJmsTemplate")
        private JmsTemplate jmsTemplate;

        /**
         * Send message. This scheduled task will place messages on the incoming queue simulating
         * line of business events flowing in from the queue. The real publisher used to place
         * messages on the outgoing queue is JmsPublisher.java
         * 
         * @throws JMSException
         */
        @Scheduled(fixedDelay = 60000) // times seconds with 1000 to get milliseconds
        public void sendTestIncomingMessage() throws JMSException {
                Event message = createTestEvent();
                JMSPublisher.publishEvent(
                        config.getHost(), 
                        config.getListenTopic(),
                        JmsMessageCreator.createMessage(MQIncomingFactory.factory, message),
                        jmsTemplate
                );
        }

        /**
         * Create event sample event message.
         *
         * @return the event message
         */
        public static Event createTestEvent() {
                return Event.builder()
                                .header(Header.builder()
                                        .messageType("Income")
                                        .subMessageType("Salary")
                                        .build()
                                )
                                .id(UUID.randomUUID().toString())
                                .name("John")
                                .surname("Doe")
                                .build();

        }

}
