package nl.mailsystem.common.gateway;

import lombok.extern.java.Log;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

import static java.util.logging.Level.SEVERE;

/**
 * @author Robin Laugs
 */
@Log
public class MessageReceiverGateway extends MessageGateway {

    private MessageConsumer consumer;

    public MessageReceiverGateway(String channel) {
        super(channel);

        try {
            consumer = session.createConsumer(destination);

            connection.start();
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message receiver gateway", e);
        }
    }

    public void setListener(MessageListener listener) {
        try {
            consumer.setMessageListener(listener);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message listener", e);
        }
    }

}
