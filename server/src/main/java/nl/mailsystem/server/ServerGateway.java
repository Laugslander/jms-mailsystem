package nl.mailsystem.server;

import nl.mailsystem.common.domain.MailDomain;

/**
 * @author Robin Laugs
 */
public class ServerGateway {

    @lombok.Getter
    private MailDomain domain;

    public ServerGateway(String domain) {
        this.domain = new MailDomain(domain);
    }

}
