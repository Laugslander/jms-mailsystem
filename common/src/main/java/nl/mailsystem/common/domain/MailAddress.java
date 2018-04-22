package nl.mailsystem.common.domain;

import lombok.Data;

import java.io.Serializable;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
@Data
public class MailAddress implements Serializable {

    private String local;
    private MailDomain domain;

    public MailAddress(String address) {
        String[] data = address.split("@");
        local = data[0];
        domain = new MailDomain(data[1]);
    }

    @Override
    public String toString() {
        return format("%s@%s", local, domain);
    }

}
