package nl.mailsystem.common.messaging.gateway;

import lombok.extern.java.Log;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static javax.naming.Context.PROVIDER_URL;

/**
 * @author Robin Laugs
 */
@Log
abstract class BaseMessageGateway {

    private static final String PROVIDER = "tcp://localhost:61616";
    private static final String CONTEXT_FACTORY = "org.apache.activemq.jndi.ActiveMQInitialContextFactory";
    private static final String CONNECTION_FACTORY = "ConnectionFactory";

    Connection connection;
    Session session;
    Destination destination;

    BaseMessageGateway(String queue, Object identifier) {
        try {
            String channel = format("%s_%s", queue, identifier);

            Properties properties = initProperties(channel);
            Context context = new InitialContext(properties);
            ConnectionFactory factory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY);

            connection = factory.createConnection();
            session = connection.createSession(false, AUTO_ACKNOWLEDGE);
            destination = (Destination) context.lookup(channel);
        } catch (NamingException | JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message messaging", e);
        }
    }

    private Properties initProperties(String queue) {
        Properties properties = new Properties();
        properties.put(PROVIDER_URL, PROVIDER);
        properties.put(INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
        properties.put(format("queue.%s", queue), queue);

        return properties;
    }

}
