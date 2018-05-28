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
public class MailDomain implements Serializable {

    private String second;
    private String top;

    public static MailDomain fromString(String domain) {
        String[] data = domain.split("\\.");

        return MailDomain.builder()
                .second(data[0])
                .top(data[1])
                .build();
    }

    @Override
    public String toString() {
        return format("%s.%s", second, top);
    }

}
