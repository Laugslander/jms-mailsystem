package nl.mailsystem.common.gateway;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
public final class QueueConstants {

    public static final String CLIENT_SERVER_REGISTRATION_QUEUE = "client_server_registration";
    public static final String CLIENT_SERVER_MAIL_QUEUE = "client_server_mail";
    public static final String SERVER_CLIENT_MAIL_QUEUE = "server_client_mail";

    private QueueConstants() {
        throw new UnsupportedOperationException(format("%s should not be instantiated", QueueConstants.class.getSimpleName()));
    }

}