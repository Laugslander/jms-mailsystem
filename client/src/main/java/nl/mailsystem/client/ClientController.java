package nl.mailsystem.client;

import lombok.Getter;
import nl.mailsystem.client.gateway.ServerGateway;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;

/**
 * @author Robin Laugs
 */
public class ClientController {

    @Getter
    private final MailAddress address;

    private ServerGateway serverGateway;

    public ClientController(String address) {
        this.address = new MailAddress(address);

        serverGateway = new ServerGateway(this.address);
    }

    public void sendMail(Mail mail) {
        mail.setSender(address);

        serverGateway.sendMail(mail);
    }

}
