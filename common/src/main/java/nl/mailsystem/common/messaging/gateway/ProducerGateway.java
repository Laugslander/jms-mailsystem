package nl.mailsystem.common.messaging.gateway;

import lombok.extern.java.Log;

import javax.jms.*;
import java.io.Serializable;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static nl.mailsystem.common.messaging.MessagingConstants.STRING_PROPERTY_CLASS_NAME;

/**
 * @author Robin Laugs
 */
@Log
public class ProducerGateway<T extends Serializable> extends BaseGateway {

    private MessageProducer producer;

    public ProducerGateway(DestinationType type, String queue, Object identifier) {
        super(type, queue, identifier);

        try {
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message producer gateway", e);
        }
    }

    public void send(T object) {
        send(object, false);
    }

    public void send(T object, boolean json) {
        try {
            Message message = json ? createTextMessage(object) : createObjectMessage(object);

            producer.send(message);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while sending a message", e);
        }
    }

    private TextMessage createTextMessage(T object) throws JMSException {
        String json = gson.toJson(object);

        TextMessage message = session.createTextMessage(json);
        message.setStringProperty(STRING_PROPERTY_CLASS_NAME, object.getClass().getName());

        log.log(INFO, "Composed a text message");

        return message;
    }

    private ObjectMessage createObjectMessage(T object) throws JMSException {
        ObjectMessage message = session.createObjectMessage(object);

        log.log(INFO, "Composed an object message");

        return message;
    }

}
