package nl.mailsystem.common.domain;

import lombok.Builder;
import lombok.Data;

import static com.github.marlonlom.utilities.timeago.TimeAgo.using;
import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
@Data
@Builder
public class Correspondence<T> {

    private T sender;
    private T receiver;
    private String subject;
    private long timestamp;

    @Override
    public String toString() {
        return format("%s to %s - %s (%s)", sender, receiver, subject, using(timestamp));
    }

}
