package nl.mailsystem.server.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.MessageReceiverGateway;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;

import static nl.mailsystem.common.messaging.QueueConstants.*;

/**
 * @author Robin Laugs
 */
public abstract class RouterGateway {

    private final MessageSenderGateway<MailDomain> registrationGateway;
    private final MessageSenderGateway<Mail> mailGateway;

    protected RouterGateway(MailDomain domain) {
        String top = domain.getTop();

        registrationGateway = new MessageSenderGateway<>(SERVER_ROUTER_REGISTRATION_QUEUE, top);
        mailGateway = new MessageSenderGateway<>(SERVER_ROUTER_MAIL_QUEUE, top);

        new MessageReceiverGateway<Mail>(ROUTER_SERVER_MAIL_QUEUE, domain) {
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
