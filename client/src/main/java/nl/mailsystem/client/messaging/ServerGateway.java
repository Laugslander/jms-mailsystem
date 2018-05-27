package nl.mailsystem.client.messaging;

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
public abstract class ServerGateway {

    private final ProducerGateway<MailAddress> registrationGateway;
    private final ProducerGateway<Mail> mailGateway;

    protected ServerGateway(MailAddress address) {
        MailDomain domain = address.getDomain();

        registrationGateway = new ProducerGateway<>(QUEUE, CLIENT_SERVER_REGISTRATION_QUEUE, domain);
        mailGateway = new ProducerGateway<>(QUEUE, CLIENT_SERVER_MAIL_QUEUE, domain);

        new ConsumerGateway<Mail>(QUEUE, SERVER_CLIENT_MAIL_QUEUE, address) {
            @Override
            public void onMessage(Mail mail) {
                onServerMail(mail);
            }
        };

        new ConsumerGateway<Mail>(TOPIC, SERVER_CLIENT_MAIL_TOPIC, address.getDomain()) {
            @Override
            protected void onMessage(Mail mail) {
                onServerMail(mail);
            }
        };

        registerMailAddress(address);
    }

    private void registerMailAddress(MailAddress address) {
        registrationGateway.send(address);
    }

    public void sendMail(Mail mail) {
        mailGateway.send(mail);
    }

    protected abstract void onServerMail(Mail mail);

}
