package com.example.demo.component;

import com.example.demo.domain.Event;
import com.example.demo.service.JmsPublisher;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import com.example.demo.utils.AppJsonUtils;

import com.example.demo.config.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


import javax.jms.JMSException;
import javax.jms.TextMessage;


/**
 * The type Message receiver.
 */
@Slf4j
@Component
public class JMSMessageReceiver {
    /**
     * The constant textMessage.
     */
    public static TextMessage textMessage;

    /**
     * The Queue publisher.
     */
    @Autowired
    JmsPublisher jmsPublisher;

    /**
     * The Config.
     */
    @Autowired
    MQOutgoingConfig config;

    @Autowired
    @Qualifier("outgoingJmsTemplate")
    private JmsTemplate jmsTemplate;


    /**
     * Receive message.
     *
     * @param message the message
     * @throws JMSException the jms exception
     */
    @JmsListener(destination = "${spring.queues.queue1.listen-topic}", containerFactory = "incomingJmsListenerContainerFactory")
    public void receiveIncomingMessage(TextMessage message) throws JMSException {
       
            log.info("\n >>>>>>>>>>>>>>>>>>>>>>>> NEW MESSAGE INCOMING >>>>>>>>>>>>>>>>>>>>>>>> \n");
            textMessage = message;
            log.info(":: Read message from queue: \n <{}> \n ::", message.getText());
            Event eventMessage = (Event) AppJsonUtils.convertMessageToClass(message.getText(), Event.class);
            log.info(":: Convert message received: \n <{}> \n ::", eventMessage);

            //Here you can do something with the received message before sending it to the outgoing queue

            // publish message to outgoing queue
            jmsPublisher.publishEvent(
                    config.getHost(),
                    config.getPublishTopic(),
                    textMessage,
                    jmsTemplate
            );
            log.info("\n <<<<<<<<<<<<<<<<<<<<<<< Message processed <<<<<<<<<<<<<<<<<<<<<<< \n");
    }
}
