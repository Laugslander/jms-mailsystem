package nl.mailsystem.server;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.mailsystem.common.domain.MailAddress;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.server.gateway.ClientGateway;

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

    public ServerController(String domain) {
        this.domain = new MailDomain(domain);

        this.clients = new HashSet<>();

        new ClientGateway(this.domain) {
            @Override
            protected void onClientRegistration(MailAddress address) {
                clients.add(address);

                log.log(INFO, format("New client with mail address %s registered", address));
            }
        };
    }

}
