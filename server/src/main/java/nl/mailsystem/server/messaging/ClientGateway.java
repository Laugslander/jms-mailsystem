package nl.mailsystem.server.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.MessageReceiverGateway;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;

import static nl.mailsystem.common.messaging.QueueConstants.*;

/**
 * @author Robin Laugs
 */
public abstract class ClientGateway {

    protected ClientGateway(MailDomain domain) {
        new MessageReceiverGateway<MailAddress>(CLIENT_SERVER_REGISTRATION_QUEUE, domain) {
            @Override
            protected void onMessage(MailAddress address) {
                onClientRegistration(address);
            }
        };

        new MessageReceiverGateway<Mail>(CLIENT_SERVER_MAIL_QUEUE, domain) {
            @Override
            protected void onMessage(Mail mail) {
                onClientMail(mail);
            }
        };
    }

    public void sendMail(Mail mail, MailAddress receiver) {
        new MessageSenderGateway<Mail>(SERVER_CLIENT_MAIL_QUEUE, receiver).send(mail);
    }

    protected abstract void onClientRegistration(MailAddress address);

    protected abstract void onClientMail(Mail mail);

}
