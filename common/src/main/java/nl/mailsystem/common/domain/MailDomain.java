package nl.mailsystem.common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Robin Laugs
 */
@Data
public class MailDomain implements Serializable {

    private String second;
    private String top;

    public MailDomain(String domain) {
        String[] data = domain.split("\\.");
        second = data[0];
        top = data[1];
    }

    @Override
    public String toString() {
        return String.format("%s.%s", second, top);
    }

}
