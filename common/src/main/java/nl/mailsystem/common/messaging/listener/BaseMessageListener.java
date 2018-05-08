package nl.mailsystem.common.messaging.listener;

import nl.mailsystem.common.messaging.gateway.MessageReceiverGateway;

import javax.jms.MessageListener;

/**
 * @author Robin Laugs
 */
public abstract class BaseMessageListener<T> implements MessageListener {

    protected BaseMessageListener(String channel) {
        new MessageReceiverGateway(channel).setListener(this);
    }

    protected abstract void onMessage(T object);

}
