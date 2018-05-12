package nl.mailsystem.server.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;
import nl.mailsystem.common.messaging.listener.MessageListener;

import static java.lang.String.format;
import static nl.mailsystem.common.messaging.QueueConstants.*;

/**
 * @author Robin Laugs
 */
public abstract class ClientGateway {

    protected ClientGateway(MailDomain domain) {
        new MessageListener<MailAddress>(format("%s_%s", CLIENT_SERVER_REGISTRATION_QUEUE, domain)) {
            @Override
            protected void onMessage(MailAddress address) {
                onClientRegistration(address);
            }
        };

        new MessageListener<Mail>(format("%s_%s", CLIENT_SERVER_MAIL_QUEUE, domain)) {
            @Override
            protected void onMessage(Mail mail) {
                onClientMail(mail);
            }
        };
    }

    public void sendMail(Mail mail, MailAddress receiver) {
        new MessageSenderGateway<Mail>(format("%s_%s", SERVER_CLIENT_MAIL_QUEUE, receiver)).send(mail);
    }

    protected abstract void onClientRegistration(MailAddress address);

    protected abstract void onClientMail(Mail mail);

}
