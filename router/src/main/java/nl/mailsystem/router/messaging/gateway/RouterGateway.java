package nl.mailsystem.router.messaging.gateway;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;
import nl.mailsystem.router.messaging.listener.RouterMailMessageListener;

import javax.jms.Message;

import static java.lang.String.format;
import static nl.mailsystem.common.messaging.QueueConstants.ROUTER_ROUTER_MAIL_QUEUE;

/**
 * @author Robin Laugs
 */
public abstract class RouterGateway {

    protected RouterGateway(String top) {
        new RouterMailMessageListener(top) {
            @Override
            protected void onMessage(Mail mail) {
                onRouterMail(mail);
            }
        };
    }

    public void sendMail(Mail mail, String receiver) {
        MessageSenderGateway mailGateway = new MessageSenderGateway(format("%s_%s", ROUTER_ROUTER_MAIL_QUEUE, receiver));

        Message message = mailGateway.createObjectMessage(mail);

        mailGateway.send(message);
    }

    protected abstract void onRouterMail(Mail mail);

}
