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
    @JmsListener(destination = "${spring.application.ibm.mq.queue1.listen-topic}", containerFactory = "incomingJmsListenerContainerFactory")
    public void receiveIncomingMessage(TextMessage message) throws JMSException {
       
            log.info("\n >>>>>>>>>>>>>>>>>>>>>>>> NEW MESSAGE INCOMING >>>>>>>>>>>>>>>>>>>>>>>> \n");
            textMessage = message;
            log.info("Read message from queue: \n <{}> \n :: red message from queue ::", message.getText());
            Event eventMessage = (Event) AppJsonUtils.convertMessageToClass(message.getText(), Event.class);
            log.info("Convert message received: \n <{}> \n :: message converted ::", eventMessage);

            // publish message to retail track and trace with the generated template
            jmsPublisher.publishEvent(
                    config.getPublishTopic(),
                    textMessage,
                    jmsTemplate
            );
            log.info("\n <<<<<<<<<<<<<<<<<<<<<<< Message processed <<<<<<<<<<<<<<<<<<<<<<< \n");
    }

    /**
     * Receive outgoing exception message.
     *
     * @param message the message
     * @throws JMSException the jms exception
     */
    @JmsListener(destination = "${spring.application.ibm.mq.queue2.listen-topic}", containerFactory = "outgoingJmsListenerContainerFactory")
    public void receiveOutgoingExceptionMessage(TextMessage message) throws JMSException {
            textMessage = message;
            JsonObject jsonObject = AppJsonUtils.convertStringToJson(message.getText());
            log.info("Read exception message from queue: \n <{}> \n :: red exception message from queue ::", 
                AppJsonUtils.convertToPrettyJsonString(jsonObject));
    }
}
