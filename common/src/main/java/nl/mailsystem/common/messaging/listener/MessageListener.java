package nl.mailsystem.common.messaging.listener;

import lombok.extern.java.Log;
import nl.mailsystem.common.messaging.gateway.MessageReceiverGateway;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import static java.util.logging.Level.SEVERE;

/**
 * @author Robin Laugs
 */
@Log
public abstract class MessageListener<T> implements javax.jms.MessageListener {

    protected MessageListener(String channel) {
        new MessageReceiverGateway(channel).setListener(this);
    }

    @Override
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
