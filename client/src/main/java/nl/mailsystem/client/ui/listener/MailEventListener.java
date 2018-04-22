package nl.mailsystem.client.ui.listener;

import nl.mailsystem.common.domain.Mail;

/**
 * @author Robin Laugs
 */
public interface MailEventListener {

    void onMailEvent(Mail mail);

}
