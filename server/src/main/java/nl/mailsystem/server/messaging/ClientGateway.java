package nl.mailsystem.server.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.ConsumerGateway;
import nl.mailsystem.common.messaging.gateway.ProducerGateway;

import static nl.mailsystem.common.messaging.MessagingConstants.*;
import static nl.mailsystem.common.messaging.gateway.DestinationType.QUEUE;
import static nl.mailsystem.common.messaging.gateway.DestinationType.TOPIC;

/**
 * @author Robin Laugs
 */
public abstract class ClientGateway {

    private final ProducerGateway<Mail> mailBroadcastGateway;

    protected ClientGateway(MailDomain domain) {
        mailBroadcastGateway = new ProducerGateway<>(TOPIC, SERVER_CLIENT_MAIL_TOPIC, domain);

        new ConsumerGateway<MailAddress>(QUEUE, CLIENT_SERVER_REGISTRATION_QUEUE, domain) {
            @Override
            protected void onMessage(MailAddress address) {
                onClientRegistration(address);
            }
        };

        new ConsumerGateway<Mail>(QUEUE, CLIENT_SERVER_MAIL_QUEUE, domain) {
            @Override
            protected void onMessage(Mail mail) {
                onClientMail(mail);
            }
        };
    }

    public void sendMail(Mail mail, MailAddress receiver) {
        new ProducerGateway<Mail>(QUEUE, SERVER_CLIENT_MAIL_QUEUE, receiver).send(mail);
    }

    public void sendMailBroadcast(Mail mail) {
        mailBroadcastGateway.send(mail);
    }

    protected abstract void onClientRegistration(MailAddress address);

    protected abstract void onClientMail(Mail mail);

}
