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
public abstract class ConsumerGateway<T extends Serializable> extends BaseGateway implements MessageListener {

    protected ConsumerGateway(DestinationType type, String queue, Object identifier) {
        super(type, queue, identifier);

        try {
            session.createConsumer(destination).setMessageListener(this);

            connection.start();
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message consumer gateway", e);
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            onMessage(message instanceof TextMessage ? receiveTextMessage(message) : receiveObjectMessage(message));
        } catch (JMSException | ClassNotFoundException e) {
            log.log(SEVERE, "An error occurred while receiving a message", e);
        }
    }

    protected abstract void onMessage(T message);

    @SuppressWarnings("unchecked")
    private T receiveTextMessage(Message message) throws JMSException, ClassNotFoundException {
        TextMessage textMessage = (TextMessage) message;
        String json = textMessage.getText();
        String className = textMessage.getStringProperty(STRING_PROPERTY_CLASS_NAME);

        log.log(INFO, "Decomposed a text message");

        return (T) gson.fromJson(json, Class.forName(className));
    }

    @SuppressWarnings("unchecked")
    private T receiveObjectMessage(Message message) throws JMSException {
        ObjectMessage objectMessage = (ObjectMessage) message;

        log.log(INFO, "Decomposed an object message");

        return (T) objectMessage.getObject();
    }

}
