package nl.mailsystem.server;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.mailsystem.common.domain.Correspondence;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.server.messaging.gateway.ClientGateway;
import nl.mailsystem.server.ui.listener.ExternalCorrespondenceEventListener;
import nl.mailsystem.server.ui.listener.InternalCorrespondenceEventListener;

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

    private Collection<MailAddress> clients = new HashSet<>();

    private ClientGateway clientGateway;

    private InternalCorrespondenceEventListener internalCorrespondenceEventListener;
    private ExternalCorrespondenceEventListener externalCorrespondenceEventListener;

    public ServerController(String domain) {
        this.domain = MailDomain.fromString(domain);

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

        log.log(INFO, format("Server with domain %s initialized", this.domain));
    }

    private void routeMail(Mail mail) {
        Collection<MailAddress> receivers = mail.getReceivers();
        receivers.stream().filter(r -> clients.contains(r)).forEach(r -> sendMailToClient(mail, r));
        receivers.stream().filter(r -> !clients.contains(r)).forEach(r -> sendMailToRouter(mail, r));
    }

    private void sendMailToClient(Mail mail, MailAddress receiver) {
        clientGateway.sendMail(mail, receiver);

        Correspondence correspondence = Correspondence.builder()
                .sender(mail.getSender())
                .receiver(receiver)
                .subject(mail.getSubject())
                .build();

        internalCorrespondenceEventListener.onInternalCorrespondenceEvent(correspondence);

        log.log(INFO, format("Mail with subject %s sent to client %s", mail.getSubject(), receiver));
    }

    private void sendMailToRouter(Mail mail, MailAddress receiver) {
        // TODO send mail to router

        Correspondence correspondence = Correspondence.builder()
                .sender(mail.getSender())
                .receiver(receiver)
                .subject(mail.getSubject())
                .build();

        externalCorrespondenceEventListener.onExternalCorrespondenceEvent(correspondence);

        log.log(INFO, format("Mail with subject %s sent to router for client %s", mail.getSubject(), receiver));
    }

    public void setInternalCorrespondenceEventListener(InternalCorrespondenceEventListener listener) {
        internalCorrespondenceEventListener = listener;
    }

    public void setExternalCorrespondenceEventListener(ExternalCorrespondenceEventListener listener) {
        externalCorrespondenceEventListener = listener;
    }

}
