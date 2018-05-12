package nl.mailsystem.server.messaging.gateway;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;
import nl.mailsystem.server.messaging.listener.RouterMailMessageListener;

import javax.jms.Message;

import static java.lang.String.format;
import static nl.mailsystem.common.messaging.QueueConstants.SERVER_ROUTER_MAIL_QUEUE;
import static nl.mailsystem.common.messaging.QueueConstants.SERVER_ROUTER_REGISTRATION_QUEUE;

/**
 * @author Robin Laugs
 */
public abstract class RouterGateway {

    private final MessageSenderGateway registrationGateway;
    private final MessageSenderGateway mailGateway;

    protected RouterGateway(MailDomain domain) {
        String top = domain.getTop();

        registrationGateway = new MessageSenderGateway(format("%s_%s", SERVER_ROUTER_REGISTRATION_QUEUE, top));
        mailGateway = new MessageSenderGateway(format("%s_%s", SERVER_ROUTER_MAIL_QUEUE, top));

        new RouterMailMessageListener(domain) {
            @Override
            protected void onMessage(Mail mail) {
                onRouterMail(mail);
            }
        };

        registerMailDomain(domain);
    }

    private void registerMailDomain(MailDomain domain) {
        Message message = registrationGateway.createObjectMessage(domain);

        registrationGateway.send(message);
    }

    public void sendMail(Mail mail) {
        Message message = mailGateway.createObjectMessage(mail);

        mailGateway.send(message);
    }

    protected abstract void onRouterMail(Mail mail);

}
