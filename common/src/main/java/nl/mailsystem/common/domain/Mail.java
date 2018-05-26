package nl.mailsystem.common.domain;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.util.Collection;

import static com.github.marlonlom.utilities.timeago.TimeAgo.using;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

/**
 * @author Robin Laugs
 */
@Data
@Builder
public class Mail implements Serializable {

    private String subject;
    private String text;

    @Default
    private long timestamp = currentTimeMillis();

    private MailAddress sender;

    @Singular
    private Collection<MailAddress> receivers;

    @Override
    public String toString() {
        return format("%s - %s (%s)", sender, subject, using(timestamp));
    }

}
