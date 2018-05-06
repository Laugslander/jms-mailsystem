package nl.mailsystem.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

import java.io.Serializable;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
@Data
@Builder
@Log
public class MailAddress implements Serializable {

    private String local;
    private MailDomain domain;

    public static MailAddress fromString(String address) {
        String[] data = address.split("@");

        return MailAddress.builder()
                .local(data[0])
                .domain(MailDomain.fromString(data[1]))
                .build();
    }

    @Override
    public String toString() {
        return format("%s@%s", local, domain);
    }

}
