package nl.mailsystem.router;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.mailsystem.common.domain.Correspondence;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.ui.listener.ExternalCorrespondenceEventListener;
import nl.mailsystem.common.ui.listener.InternalCorrespondenceEventListener;
import nl.mailsystem.router.messaging.RouterGateway;
import nl.mailsystem.router.messaging.ServerGateway;

import java.util.Collection;
import java.util.HashSet;

import static java.lang.String.format;
import static java.util.logging.Level.INFO;

/**
 * @author Robin Laugs
 */
@Log
public class RouterController {

    @Getter
    private String top;

    private Collection<MailDomain> domains = new HashSet<>();

    private ServerGateway serverGateway;
    private RouterGateway routerGateway;

    private InternalCorrespondenceEventListener internalCorrespondenceEventListener;
    private ExternalCorrespondenceEventListener externalCorrespondenceEventListener;

    public RouterController(String top) {
        this.top = top;

        serverGateway = new ServerGateway(top) {
            @Override
            protected void onServerRegistration(MailDomain domain) {
                if (domains.add(domain)) {
                    log.log(INFO, "Server with mail domain %s registered", domain);
                }
            }

            @Override
            protected void onServerMail(Mail mail) {
                log.log(INFO, format("Mail with subject %s received from server %s",
                        mail.getSubject(),
                        mail.getSender().getDomain()));

                routeMail(mail);
            }
        };

        routerGateway = new RouterGateway(top) {
            @Override
            protected void onRouterMail(Mail mail) {
                log.log(INFO, format("Mail with subject %s received from router %s",
                        mail.getSubject(),
                        mail.getSender().getDomain().getTop()));

                sendMailToServers(mail);
            }
        };

        log.log(INFO, format("Server with domain %s initialized", top));
    }

    private void routeMail(Mail mail) {
        sendMailToServers(mail);
        sendMailToRouters(mail);
    }

    private void sendMailToServers(Mail mail) {
        mail.getReceivers()
                .stream()
                .map(MailAddress::getDomain)
                .filter(r -> domains.contains(r))
                .forEach(r -> sendMailToServer(mail, r));
    }

    private void sendMailToRouters(Mail mail) {
        mail.getReceivers()
                .stream()
                .map(MailAddress::getDomain)
                .filter(r -> !domains.contains(r))
                .forEach(r -> sendMailToRouter(mail, r));
    }

    private void sendMailToServer(Mail mail, MailDomain receiver) {
        serverGateway.sendMail(mail, receiver);

        String subject = mail.getSubject();

        Correspondence correspondence = Correspondence.builder()
                .sender(mail.getSender())
                .receiver(receiver)
                .subject(subject)
                .build();

        internalCorrespondenceEventListener.onInternalCorrespondenceEvent(correspondence);

        log.log(INFO, format("Mail with subject %s sent to server %s", subject, receiver));
    }

    private void sendMailToRouter(Mail mail, MailDomain receiver) {
        routerGateway.sendMail(mail, receiver.getTop());

        String subject = mail.getSubject();

        Correspondence correspondence = Correspondence.builder()
                .sender(mail.getSender())
                .receiver(receiver)
                .subject(subject)
                .build();

        externalCorrespondenceEventListener.onExternalCorrespondenceEvent(correspondence);

        log.log(INFO, format("Mail with subject %s sent to router %s", subject, receiver.getTop()));
    }


    public void setInternalCorrespondenceEventListener(InternalCorrespondenceEventListener listener) {
        internalCorrespondenceEventListener = listener;
    }

    public void setExternalCorrespondenceEventListener(ExternalCorrespondenceEventListener listener) {
        externalCorrespondenceEventListener = listener;
    }

}
