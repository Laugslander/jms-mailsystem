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
public abstract class MessageReceiverGateway<T extends Serializable> extends BaseMessageGateway implements MessageListener {

    protected MessageReceiverGateway(String queue, Object identifier) {
        super(queue, identifier);

        try {
            session.createConsumer(destination).setMessageListener(this);

            connection.start();
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while setting up a message receiver gateway", e);
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
