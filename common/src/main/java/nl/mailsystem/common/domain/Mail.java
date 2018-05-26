package nl.mailsystem.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.util.Collection;

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

    @Singular
    private Collection<MailAddress> receivers;

    @Override
    public String toString() {
        return format("%s - %s", sender, subject);
    }

}
