package nl.mailsystem.common.messaging.gateway;

import lombok.extern.java.Log;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import static nl.mailsystem.common.messaging.gateway.DestinationType.QUEUE;
import static org.apache.activemq.ActiveMQConnection.DEFAULT_BROKER_URL;

/**
 * @author Robin Laugs
 */
@Log
abstract class BaseGateway {

    Connection connection;
    Session session;
    Destination destination;

    BaseGateway(DestinationType type, String queue, Object identifier) {
        try {
            connection = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL).createConnection();
            session = connection.createSession(false, AUTO_ACKNOWLEDGE);

            String channel = format("%s_%s", queue, identifier);
            destination = type.equals(QUEUE) ? session.createQueue(channel) : session.createTopic(channel);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message gateway", e);
        }
    }

}
