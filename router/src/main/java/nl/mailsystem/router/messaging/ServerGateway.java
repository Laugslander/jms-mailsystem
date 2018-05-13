package nl.mailsystem.router.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.MessageReceiverGateway;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;

import static nl.mailsystem.common.messaging.QueueConstants.*;

/**
 * @author Robin Laugs
 */
public abstract class ServerGateway {

    protected ServerGateway(String top) {
        new MessageReceiverGateway<MailDomain>(SERVER_ROUTER_REGISTRATION_QUEUE, top) {
            @Override
            protected void onMessage(MailDomain domain) {
                onServerRegistration(domain);
            }
        };

        new MessageReceiverGateway<Mail>(SERVER_ROUTER_MAIL_QUEUE, top) {
            @Override
            protected void onMessage(Mail mail) {
                onServerMail(mail);
            }
        };
    }

    public void sendMail(Mail mail, MailDomain receiver) {
        new MessageSenderGateway<Mail>(ROUTER_SERVER_MAIL_QUEUE, receiver).send(mail);
    }

    protected abstract void onServerRegistration(MailDomain domain);

    protected abstract void onServerMail(Mail mail);

}
