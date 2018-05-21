package nl.mailsystem.router.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.ConsumerGateway;
import nl.mailsystem.common.messaging.gateway.ProducerGateway;

import static nl.mailsystem.common.messaging.QueueConstants.*;
import static nl.mailsystem.common.messaging.gateway.DestinationType.QUEUE;

/**
 * @author Robin Laugs
 */
public abstract class ServerGateway {

    protected ServerGateway(String top) {
        new ConsumerGateway<MailDomain>(QUEUE, SERVER_ROUTER_REGISTRATION_QUEUE, top) {
            @Override
            protected void onMessage(MailDomain domain) {
                onServerRegistration(domain);
            }
        };

        new ConsumerGateway<Mail>(QUEUE, SERVER_ROUTER_MAIL_QUEUE, top) {
            @Override
            protected void onMessage(Mail mail) {
                onServerMail(mail);
            }
        };
    }

    public void sendMail(Mail mail, MailDomain receiver) {
        new ProducerGateway<Mail>(QUEUE, ROUTER_SERVER_MAIL_QUEUE, receiver).send(mail);
    }

    protected abstract void onServerRegistration(MailDomain domain);

    protected abstract void onServerMail(Mail mail);

}
