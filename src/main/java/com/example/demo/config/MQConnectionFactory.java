package com.example.demo.config;

import com.ibm.mq.jms.MQXAConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * The type Mq connection factory.
 */
@Slf4j
@Configuration
public class MQConnectionFactory {

    /**
     * Default jms listener container factory default jms listener container factory.
     *
     * @param factory the factory
     * @param config  the config
     * @return the default jms listener container factory
     */
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(ConnectionFactory factory, MQConfig config) {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setConnectionFactory(factory);
        containerFactory.setConcurrency(config.getConcurrent());
        containerFactory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        containerFactory.setSessionTransacted(true);
        containerFactory.setReceiveTimeout(5000L);
        containerFactory.setPubSubDomain(config.isPubSub()); //https://www.ibm.com/support/knowledgecenter/SSFKSJ_9.1.0/com.ibm.mq.pro.doc/q004890_.htm
//        containerFactory.setErrorHandler(new JMSListenerErrorHandler());
        return containerFactory;
    }

    /**
     * Connection factory connection factory.
     *
     * @param factory the factory
     * @param config  the config
     * @return the connection factory
     */
    public ConnectionFactory connectionFactory(ConnectionFactory factory, MQConfig config) {
        if (factory == null) {
            MQXAConnectionFactory connectionFactory = new MQXAConnectionFactory();
            UserCredentialsConnectionFactoryAdapter credentials = new UserCredentialsConnectionFactoryAdapter();

            log.info(String.format("\n /************** MQ CONFIG \n %s \n **************/", config.toString()));

            try {
                connectionFactory.setHostName(config.getHost());
                connectionFactory.setPort(config.getPort());
                connectionFactory.setQueueManager(config.getQueueManager());
                connectionFactory.setChannel(config.getChannel());
                connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
                connectionFactory.setAppName(config.getAppName());
                connectionFactory.setDescription(config.getAppDescription());

                if (config.getUserName() != null) {
                    credentials.setUsername(config.getUserName());
                    credentials.setPassword(config.getPassword());
                }

                credentials.setTargetConnectionFactory(connectionFactory);
                factory = credentials;

            } catch (JMSException e) {
                throw new RuntimeException(e);
            }

        }

        return factory;
    }

}
