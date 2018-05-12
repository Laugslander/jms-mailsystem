package nl.mailsystem.server;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.mailsystem.common.domain.Correspondence;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.ui.listener.ExternalCorrespondenceEventListener;
import nl.mailsystem.common.ui.listener.InternalCorrespondenceEventListener;
import nl.mailsystem.server.messaging.ClientGateway;
import nl.mailsystem.server.messaging.RouterGateway;

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

    private Collection<MailAddress> addresses = new HashSet<>();

    private ClientGateway clientGateway;
    private RouterGateway routerGateway;

    private InternalCorrespondenceEventListener internalCorrespondenceEventListener;
    private ExternalCorrespondenceEventListener externalCorrespondenceEventListener;

    public ServerController(String domain) {
        this.domain = MailDomain.fromString(domain);

        clientGateway = new ClientGateway(this.domain) {
            @Override
            protected void onClientRegistration(MailAddress address) {
                if (addresses.add(address)) {
                    log.log(INFO, format("Client with mail address %s registered", address));
                }
            }

            @Override
            protected void onClientMail(Mail mail) {
                log.log(INFO, format("Mail with subject %s received from client %s",
                        mail.getSubject(),
                        mail.getSender()));

                routeMail(mail);
            }
        };

        routerGateway = new RouterGateway(this.domain) {
            @Override
            protected void onRouterMail(Mail mail) {
                log.log(INFO, format("Mail with subject %s received from router %s",
                        mail.getSubject(),
                        mail.getSender().getDomain().getTop()));

                sendMailToClients(mail);
            }
        };

        log.log(INFO, format("Server with domain %s initialized", this.domain));
    }

    private void routeMail(Mail mail) {
        sendMailToClients(mail);
        sendMailToRouters(mail);
    }

    private void sendMailToClients(Mail mail) {
        mail.getReceivers()
                .stream()
                .filter(r -> addresses.contains(r))
                .forEach(r -> sendMailToClient(mail, r));
    }

    private void sendMailToRouters(Mail mail) {
        mail.getReceivers()
                .stream()
                .filter(r -> !addresses.contains(r))
                .forEach(r -> sendMailToRouter(mail, r));
    }

    private void sendMailToClient(Mail mail, MailAddress receiver) {
        clientGateway.sendMail(mail, receiver);

        String subject = mail.getSubject();

        Correspondence correspondence = Correspondence.builder()
                .sender(mail.getSender())
                .receiver(receiver)
                .subject(subject)
                .build();

        internalCorrespondenceEventListener.onInternalCorrespondenceEvent(correspondence);

        log.log(INFO, format("Mail with subject %s sent to client %s", subject, receiver));
    }

    private void sendMailToRouter(Mail mail, MailAddress receiver) {
        routerGateway.sendMail(mail);

        String subject = mail.getSubject();

        Correspondence correspondence = Correspondence.builder()
                .sender(mail.getSender())
                .receiver(receiver)
                .subject(subject)
                .build();

        externalCorrespondenceEventListener.onExternalCorrespondenceEvent(correspondence);

        log.log(INFO, format("Mail with subject %s sent to router %s", subject, receiver.getDomain().getTop()));
    }

    public void setInternalCorrespondenceEventListener(InternalCorrespondenceEventListener listener) {
        internalCorrespondenceEventListener = listener;
    }

    public void setExternalCorrespondenceEventListener(ExternalCorrespondenceEventListener listener) {
        externalCorrespondenceEventListener = listener;
    }

}
