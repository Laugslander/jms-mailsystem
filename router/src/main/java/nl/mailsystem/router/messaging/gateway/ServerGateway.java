package nl.mailsystem.router.messaging.gateway;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;
import nl.mailsystem.router.messaging.listener.ServerMailMessageListener;
import nl.mailsystem.router.messaging.listener.ServerRegistrationMessageListener;

import javax.jms.Message;

import static java.lang.String.format;
import static nl.mailsystem.common.messaging.QueueConstants.ROUTER_SERVER_MAIL_QUEUE;

/**
 * @author Robin Laugs
 */
public abstract class ServerGateway {

    protected ServerGateway(String top) {
        new ServerRegistrationMessageListener(top) {
            @Override
            protected void onMessage(MailDomain domain) {
                onServerRegistration(domain);
            }
        };

        new ServerMailMessageListener(top) {
            @Override
            protected void onMessage(Mail mail) {
                onServerMail(mail);
            }
        };
    }

    public void sendMail(Mail mail, MailDomain receiver) {
        MessageSenderGateway mailGateway = new MessageSenderGateway(format("%s_%s", ROUTER_SERVER_MAIL_QUEUE, receiver));

        Message message = mailGateway.createObjectMessage(mail);

        mailGateway.send(message);
    }

    protected abstract void onServerRegistration(MailDomain domain);

    protected abstract void onServerMail(Mail mail);

}
