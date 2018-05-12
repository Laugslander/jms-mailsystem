package nl.mailsystem.client.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;
import nl.mailsystem.common.messaging.listener.MessageListener;

import static java.lang.String.format;
import static nl.mailsystem.common.messaging.QueueConstants.*;

/**
 * @author Robin Laugs
 */
public abstract class ServerGateway {

    private final MessageSenderGateway<MailAddress> registrationGateway;
    private final MessageSenderGateway<Mail> mailGateway;

    protected ServerGateway(MailAddress address) {
        MailDomain domain = address.getDomain();

        registrationGateway = new MessageSenderGateway<>(format("%s_%s", CLIENT_SERVER_REGISTRATION_QUEUE, domain));
        mailGateway = new MessageSenderGateway<>(format("%s_%s", CLIENT_SERVER_MAIL_QUEUE, domain));

        new MessageListener<Mail>(format("%s_%s", SERVER_CLIENT_MAIL_QUEUE, address)) {
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
