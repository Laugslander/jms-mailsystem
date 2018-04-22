package nl.mailsystem.common.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
@Data
@Builder
public class Mail implements Serializable {

    private String subject;
    private String text;

    private MailAddress sender;
    private MailAddress receiver;

    private Mail reply;

    @Override
    public String toString() {
        return format("%s - %s", sender, subject);
    }
}
