package nl.mailsystem.server.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.ConsumerGateway;
import nl.mailsystem.common.messaging.gateway.ProducerGateway;

import static nl.mailsystem.common.messaging.MessagingConstants.*;
import static nl.mailsystem.common.messaging.gateway.DestinationType.QUEUE;

/**
 * @author Robin Laugs
 */
public abstract class RouterGateway {

    private final ProducerGateway<MailDomain> registrationGateway;
    private final ProducerGateway<Mail> mailGateway;

    protected RouterGateway(MailDomain domain) {
        String top = domain.getTop();

        registrationGateway = new ProducerGateway<>(QUEUE, SERVER_ROUTER_REGISTRATION_QUEUE, top);
        mailGateway = new ProducerGateway<>(QUEUE, SERVER_ROUTER_MAIL_QUEUE, top);

        new ConsumerGateway<Mail>(QUEUE, ROUTER_SERVER_MAIL_QUEUE, domain) {
            @Override
            protected void onMessage(Mail mail) {
                onRouterMail(mail);
            }
        };

        registerMailDomain(domain);
    }

    private void registerMailDomain(MailDomain domain) {
        registrationGateway.send(domain);
    }

    public void sendMail(Mail mail) {
        mailGateway.send(mail);
    }

    protected abstract void onRouterMail(Mail mail);

}
