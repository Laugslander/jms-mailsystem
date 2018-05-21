package nl.mailsystem.common.messaging.gateway;

import lombok.extern.java.Log;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

import static java.util.logging.Level.SEVERE;

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
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            T object = (T) objectMessage.getObject();

            onMessage(object);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while receiving a message", e);
        }
    }

    protected abstract void onMessage(T object);

}
