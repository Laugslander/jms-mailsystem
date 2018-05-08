package nl.mailsystem.common.messaging.gateway;

import lombok.extern.java.Log;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import java.io.Serializable;

import static java.util.logging.Level.SEVERE;

/**
 * @author Robin Laugs
 */
@Log
public class MessageSenderGateway extends BaseMessageGateway {

    private MessageProducer producer;

    public MessageSenderGateway(String queue) {
        super(queue);

        try {
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message sender messaging", e);
        }
    }

    public Message createObjectMessage(Serializable object) {
        try {
            return session.createObjectMessage(object);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while creating an object message", e);

            return null;
        }
    }

    public void send(Message message) {
        try {
            producer.send(message);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while sending a message", e);
        }
    }

}
