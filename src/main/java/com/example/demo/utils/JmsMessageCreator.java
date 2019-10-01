package com.example.demo.utils;

import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

import com.example.demo.domain.Event;

/**
 * The type Jms message creator.
 */
public class JmsMessageCreator {

    /**
     * Create message text message.
     *
     * @param factory the factory
     * @param message the message
     * @return the text message
     * @throws JMSException the jms exception
     */
    public static TextMessage createMessage(ConnectionFactory factory, Event message) throws JMSException {
        Connection connection = null;
        Session connectionSession = null;
        try {
            MessageCreator textMessageCreator = (Session session) -> {
                TextMessage m = session.createTextMessage();
                m.setStringProperty("messageType", message.getHeader().getMessageType());
                m.setStringProperty("messageSubType", message.getHeader().getSubMessageType());
                m.setText(AppJsonUtils.convertToPrettyJsonString(message));
                return m;
            };

            connection = factory.createConnection();
            connection.start();
            connectionSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            return (TextMessage) textMessageCreator.createMessage(connectionSession);
        } catch (Exception e) {
            throw e;
        } finally {
            if (connectionSession != null) {
                connectionSession.close();
            }
            if (connection != null) {
                connection.close();
            }
        }


    }
}
