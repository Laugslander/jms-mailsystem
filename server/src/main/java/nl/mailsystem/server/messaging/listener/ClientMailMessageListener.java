package nl.mailsystem.server.messaging.listener;

import lombok.extern.java.Log;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.gateway.MessageReceiverGateway;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static nl.mailsystem.common.gateway.QueueConstants.CLIENT_SERVER_MAIL_QUEUE;

/**
 * @author Robin Laugs
 */
@Log
public abstract class ClientMailMessageListener implements MessageListener {

    protected ClientMailMessageListener(MailDomain domain) {
        new MessageReceiverGateway(format("%s_%s", CLIENT_SERVER_MAIL_QUEUE, domain)).setListener(this);
    }

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Mail mail = (Mail) objectMessage.getObject();

            onClientMailMessage(mail);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while receiving a mail message from a client", e);
        }
    }

    protected abstract void onClientMailMessage(Mail mail);

}
