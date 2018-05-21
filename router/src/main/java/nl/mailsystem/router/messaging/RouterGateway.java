package nl.mailsystem.router.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.messaging.gateway.ConsumerGateway;
import nl.mailsystem.common.messaging.gateway.ProducerGateway;

import static nl.mailsystem.common.messaging.QueueConstants.ROUTER_ROUTER_MAIL_QUEUE;
import static nl.mailsystem.common.messaging.gateway.DestinationType.QUEUE;

/**
 * @author Robin Laugs
 */
public abstract class RouterGateway {

    protected RouterGateway(String top) {
        new ConsumerGateway<Mail>(QUEUE, ROUTER_ROUTER_MAIL_QUEUE, top) {
            @Override
            protected void onMessage(Mail mail) {
                onRouterMail(mail);
            }
        };
    }

    public void sendMail(Mail mail, String receiver) {
        new ProducerGateway<Mail>(QUEUE, ROUTER_ROUTER_MAIL_QUEUE, receiver).send(mail);
    }

    protected abstract void onRouterMail(Mail mail);

}
