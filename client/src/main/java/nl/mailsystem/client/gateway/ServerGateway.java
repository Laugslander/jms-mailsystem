package nl.mailsystem.client.gateway;

import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.gateway.MessageSenderGateway;

import javax.jms.Message;

import static java.lang.String.format;
import static nl.mailsystem.common.gateway.QueueConstants.CLIENT_REGISTRATION_QUEUE;
import static nl.mailsystem.common.gateway.QueueConstants.CLIENT_SEND_QUEUE;

/**
 * @author Robin Laugs
 */
public class ServerGateway {

    private final MessageSenderGateway registrationSenderGateway;
    private final MessageSenderGateway mailSenderGateway;

    public ServerGateway(MailAddress address) {
        MailDomain domain = address.getDomain();

        registrationSenderGateway = new MessageSenderGateway(format("%s_%s", CLIENT_REGISTRATION_QUEUE, domain));
        mailSenderGateway = new MessageSenderGateway(format("%s_%s", CLIENT_SEND_QUEUE, domain));

        registerMailAddress(address);
    }

    private void registerMailAddress(MailAddress address) {
        Message message = registrationSenderGateway.createObjectMessage(address);

        registrationSenderGateway.send(message);
    }

    public void sendMail(Mail mail) {
        Message message = mailSenderGateway.createObjectMessage(mail);

        mailSenderGateway.send(message);
    }

}
