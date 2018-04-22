package nl.mailsystem.common.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

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

}
