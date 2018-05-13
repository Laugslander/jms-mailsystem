package nl.mailsystem.client.messaging;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.messaging.gateway.MessageReceiverGateway;
import nl.mailsystem.common.messaging.gateway.MessageSenderGateway;

import static nl.mailsystem.common.messaging.QueueConstants.*;

/**
 * @author Robin Laugs
 */
public abstract class ServerGateway {

    private final MessageSenderGateway<MailAddress> registrationGateway;
    private final MessageSenderGateway<Mail> mailGateway;

    protected ServerGateway(MailAddress address) {
        MailDomain domain = address.getDomain();

        registrationGateway = new MessageSenderGateway<>(CLIENT_SERVER_REGISTRATION_QUEUE, domain);
        mailGateway = new MessageSenderGateway<>(CLIENT_SERVER_MAIL_QUEUE, domain);

        new MessageReceiverGateway<Mail>(SERVER_CLIENT_MAIL_QUEUE, address) {
            @Override
            public void onMessage(Mail mail) {
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
