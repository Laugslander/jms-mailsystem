package nl.mailsystem.server.messaging.listener;

import lombok.extern.java.Log;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.gateway.MessageReceiverGateway;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static nl.mailsystem.common.gateway.QueueConstants.CLIENT_SERVER_REGISTRATION_QUEUE;

/**
 * @author Robin Laugs
 */
@Log
public abstract class ClientRegistrationMessageListener implements MessageListener {

    protected ClientRegistrationMessageListener(MailDomain domain) {
        new MessageReceiverGateway(format("%s_%s", CLIENT_SERVER_REGISTRATION_QUEUE, domain)).setListener(this);
    }

    protected abstract void onClientRegistrationMessage(MailAddress address);

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            MailAddress address = (MailAddress) objectMessage.getObject();

            onClientRegistrationMessage(address);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while receiving a registration message from a client", e);
        }
    }

}
