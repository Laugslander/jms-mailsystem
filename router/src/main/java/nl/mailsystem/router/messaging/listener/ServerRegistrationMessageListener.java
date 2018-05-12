package nl.mailsystem.router.messaging.listener;

import lombok.extern.java.Log;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.listener.BaseMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static nl.mailsystem.common.messaging.QueueConstants.SERVER_ROUTER_REGISTRATION_QUEUE;

/**
 * @author Robin Laugs
 */
@Log
public abstract class ServerRegistrationMessageListener extends BaseMessageListener<MailDomain> {

    protected ServerRegistrationMessageListener(String top) {
        super(format("%s_%s", SERVER_ROUTER_REGISTRATION_QUEUE, top));
    }

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            MailDomain domain = (MailDomain) objectMessage.getObject();

            onMessage(domain);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while receiving a registration message from a server", e);
        }
    }
}
