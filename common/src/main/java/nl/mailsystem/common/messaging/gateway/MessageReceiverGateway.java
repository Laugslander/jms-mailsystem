package nl.mailsystem.common.messaging.gateway;

import lombok.extern.java.Log;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

import static java.util.logging.Level.SEVERE;

/**
 * @author Robin Laugs
 */
@Log
public class MessageReceiverGateway extends BaseMessageGateway {

    private MessageConsumer consumer;

    public MessageReceiverGateway(String queue) {
        super(queue);

        try {
            consumer = session.createConsumer(destination);

            connection.start();
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message receiver messaging", e);
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
