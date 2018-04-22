package nl.mailsystem.client.messaging;

import nl.mailsystem.client.messaging.listener.ServerMailListener;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.gateway.MessageSenderGateway;

import javax.jms.Message;

import static java.lang.String.format;
import static nl.mailsystem.common.gateway.QueueConstants.CLIENT_SERVER_MAIL_QUEUE;
import static nl.mailsystem.common.gateway.QueueConstants.CLIENT_SERVER_REGISTRATION_QUEUE;

/**
 * @author Robin Laugs
 */
public abstract class ServerGateway {

    private final MessageSenderGateway registrationGateway;
    private final MessageSenderGateway mailGateway;

    public ServerGateway(MailAddress address) {
        MailDomain domain = address.getDomain();

        registrationGateway = new MessageSenderGateway(format("%s_%s", CLIENT_SERVER_REGISTRATION_QUEUE, domain));
        mailGateway = new MessageSenderGateway(format("%s_%s", CLIENT_SERVER_MAIL_QUEUE, domain));

        new ServerMailListener(domain) {
            @Override
            protected void onServerMailMessage(Mail mail) {
                onServerMail(mail);
            }
        };

        registerMailAddress(address);
    }

    private void registerMailAddress(MailAddress address) {
        Message message = registrationGateway.createObjectMessage(address);

        registrationGateway.send(message);
    }

    public void sendMail(Mail mail) {
        Message message = mailGateway.createObjectMessage(mail);

        mailGateway.send(message);
    }

    protected abstract void onServerMail(Mail mail);


}
