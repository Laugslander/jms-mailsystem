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
public class MessageSenderGateway<T extends Serializable> extends BaseMessageGateway {

    private MessageProducer producer;

    public MessageSenderGateway(String queue) {
        super(queue);

        try {
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message sender messaging", e);
        }
    }

    public void send(T object) {
        try {
            Message message = session.createObjectMessage(object);

            producer.send(message);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while sending a message", e);
        }
    }

}
