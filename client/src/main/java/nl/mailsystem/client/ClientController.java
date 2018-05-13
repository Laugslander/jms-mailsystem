package nl.mailsystem.client;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.mailsystem.client.messaging.ServerGateway;
import nl.mailsystem.client.ui.listener.MailEventListener;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;

import java.util.Collection;
import java.util.LinkedList;

import static java.lang.String.format;
import static java.util.logging.Level.INFO;

/**
 * @author Robin Laugs
 */
@Log
public class ClientController {

    @Getter
    private final MailAddress address;

    @Getter
    private Collection<Mail> mails = new LinkedList<>();

    private ServerGateway serverGateway;

    private MailEventListener mailEventListener;

    public ClientController(String address) {
        this.address = MailAddress.fromString(address);

        serverGateway = new ServerGateway(this.address) {
            @Override
            protected void onServerMail(Mail mail) {
                if (mails.add(mail)) {
                    log.log(INFO, format("Mail with subject %s received from server", mail.getSubject()));

                    mailEventListener.onMailEvent(mail);
                }
            }
        };

        log.log(INFO, format("Client with address %s initialized", this.address));
    }

    public void sendMailToServer(Mail mail) {
        mail.setSender(address);

        serverGateway.sendMail(mail);

        log.log(INFO, format("Mail with subject %s sent to server", mail.getSubject()));
    }

    public void setMailEventListener(MailEventListener listener) {
        mailEventListener = listener;
    }

}
