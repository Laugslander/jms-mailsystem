package nl.mailsystem.router.messaging.listener;

import lombok.extern.java.Log;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.messaging.listener.BaseMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static nl.mailsystem.common.messaging.QueueConstants.ROUTER_ROUTER_MAIL_QUEUE;

/**
 * @author Robin Laugs
 */
@Log
public abstract class RouterMailMessageListener extends BaseMessageListener<Mail> {

    protected RouterMailMessageListener(String top) {
        super(format("%s_%s", ROUTER_ROUTER_MAIL_QUEUE, top));
    }

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Mail mail = (Mail) objectMessage.getObject();

            onMessage(mail);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while receiving a mail message from a router", e);
        }
    }

}
