package nl.mailsystem.client;

import lombok.Getter;
import nl.mailsystem.common.domain.MailAddress;

/**
 * @author Robin Laugs
 */
public class ClientGateway {

    @Getter
    private final MailAddress address;

    public ClientGateway(String address) {
        this.address = new MailAddress(address);
    }

}
