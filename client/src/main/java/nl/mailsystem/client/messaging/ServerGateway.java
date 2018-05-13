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

    private MessageSenderGateway<MailAddress> registrationGateway;
    private MessageSenderGateway<Mail> mailGateway;

    protected ServerGateway(MailAddress address) {
        initializeGateways(address);
        registerMailAddress(address);
    }

    private void initializeGateways(MailAddress address) {
        MailDomain domain = address.getDomain();

        registrationGateway = new MessageSenderGateway<>(CLIENT_SERVER_REGISTRATION_QUEUE, domain);
        mailGateway = new MessageSenderGateway<>(CLIENT_SERVER_MAIL_QUEUE, domain);

        new MessageReceiverGateway<Mail>(SERVER_CLIENT_MAIL_QUEUE, address) {
            @Override
            protected void onMessage(Mail mail) {
                onServerMail(mail);
            }
        };
    }

    private void registerMailAddress(MailAddress address) {
        registrationGateway.send(address);
    }

    public void sendMail(Mail mail) {
        mailGateway.send(mail);
    }

    protected abstract void onServerMail(Mail mail);

}
