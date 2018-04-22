package nl.mailsystem.server;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.server.messaging.ClientGateway;

import java.util.Collection;
import java.util.HashSet;

import static java.lang.String.format;
import static java.util.logging.Level.INFO;

/**
 * @author Robin Laugs
 */
@Log
public class ServerController {

    @Getter
    private MailDomain domain;

    private Collection<MailAddress> clients;

    private ClientGateway clientGateway;

    public ServerController(String domain) {
        this.domain = new MailDomain(domain);

        clients = new HashSet<>();

        log.log(INFO, format("Server with domain %s initialized", this.domain));

        clientGateway = new ClientGateway(this.domain) {
            @Override
            protected void onClientRegistration(MailAddress address) {
                if (clients.add(address)) {
                    log.log(INFO, format("New client with mail address %s registered", address));
                }
            }

            @Override
            protected void onClientMail(Mail mail) {
                log.log(INFO, format("Mail with subject %s received from client %s", mail.getSubject(), mail.getSender()));

                routeMail(mail);
            }
        };
    }

    private void routeMail(Mail mail) {
        Collection<MailAddress> receivers = mail.getReceivers();
        receivers.stream().filter(r -> clients.contains(r)).forEach(r -> sendMailToClient(mail, r));
        receivers.stream().filter(r -> !clients.contains(r)).forEach(r -> sendMailToRouter(mail, r));
    }

    private void sendMailToClient(Mail mail, MailAddress receiver) {
        clientGateway.sendMail(mail, receiver);

        log.log(INFO, format("Mail with subject %s sent to client %s", mail.getSubject(), receiver));
    }

    private void sendMailToRouter(Mail mail, MailAddress receiver) {
        // TODO send mail to router

        log.log(INFO, format("Mail with subject %s sent to router for client %s", mail.getSubject(), receiver));
    }

}
