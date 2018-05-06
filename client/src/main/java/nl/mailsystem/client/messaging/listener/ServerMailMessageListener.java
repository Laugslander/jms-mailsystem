package nl.mailsystem.client.messaging.listener;

import lombok.extern.java.Log;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.gateway.MessageReceiverGateway;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static nl.mailsystem.common.gateway.QueueConstants.SERVER_CLIENT_MAIL_QUEUE;

/**
 * @author Robin Laugs
 */
@Log
public abstract class ServerMailMessageListener implements MessageListener {

    protected ServerMailMessageListener(MailAddress address) {
        new MessageReceiverGateway(format("%s_%s", SERVER_CLIENT_MAIL_QUEUE, address)).setListener(this);
    }

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Mail mail = (Mail) objectMessage.getObject();

            onServerMailMessage(mail);
        } catch (JMSException e) {
            log.log(SEVERE, "An error occurred while receiving a mail message from a server", e);
        }
    }

    protected abstract void onServerMailMessage(Mail mail);

}