package nl.mailsystem.common.domain;

import lombok.Builder;
import lombok.Data;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
@Data
@Builder
public class Correspondence {

    private MailAddress sender;
    private MailAddress receiver;
    private String subject;

    @Override
    public String toString() {
        return format("%s to %s - %s", sender, receiver, subject);
    }

}
