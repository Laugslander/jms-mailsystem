package nl.mailsystem.router.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;
import nl.mailsystem.common.messaging.listener.MessageListener;

import static java.lang.String.format;
import static nl.mailsystem.common.messaging.QueueConstants.*;

/**
 * @author Robin Laugs
 */
public abstract class ServerGateway {

    protected ServerGateway(String top) {
        new MessageListener<MailDomain>(format("%s_%s", SERVER_ROUTER_REGISTRATION_QUEUE, top)) {
            @Override
            protected void onMessage(MailDomain domain) {
                onServerRegistration(domain);
            }
        };

        new MessageListener<Mail>(format("%s_%s", SERVER_ROUTER_MAIL_QUEUE, top)) {
            @Override
            protected void onMessage(Mail mail) {
                onServerMail(mail);
            }
        };
    }

    public void sendMail(Mail mail, MailDomain receiver) {
        new MessageSenderGateway<Mail>(format("%s_%s", ROUTER_SERVER_MAIL_QUEUE, receiver)).send(mail);
    }

    protected abstract void onServerRegistration(MailDomain domain);

    protected abstract void onServerMail(Mail mail);

}
