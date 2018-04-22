package nl.mailsystem.server.messaging;

import lombok.extern.java.Log;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.gateway.MessageSenderGateway;
import nl.mailsystem.server.messaging.listener.ClientMailMessageListener;
import nl.mailsystem.server.messaging.listener.ClientRegistrationMessageListener;

import javax.jms.Message;

import static java.lang.String.format;
import static nl.mailsystem.common.gateway.QueueConstants.SERVER_CLIENT_MAIL_QUEUE;

/**
 * @author Robin Laugs
 */
@Log
public abstract class ClientGateway {

    protected ClientGateway(MailDomain domain) {
        new ClientRegistrationMessageListener(domain) {
            @Override
            protected void onClientRegistrationMessage(MailAddress address) {
                onClientRegistration(address);
            }
        };

        new ClientMailMessageListener(domain) {
            @Override
            protected void onClientMailMessage(Mail mail) {
                onClientMail(mail);
            }
        };
    }

    public void sendMail(Mail mail) {
        MessageSenderGateway mailGateway = new MessageSenderGateway(format("%s_%s", SERVER_CLIENT_MAIL_QUEUE, mail.getReceiver()));

        Message message = mailGateway.createObjectMessage(mail);

        mailGateway.send(message);
    }

    protected abstract void onClientRegistration(MailAddress address);

    protected abstract void onClientMail(Mail mail);

}
