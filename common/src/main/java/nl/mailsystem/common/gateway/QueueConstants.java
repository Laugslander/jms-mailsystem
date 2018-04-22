package nl.mailsystem.common.gateway;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
public final class QueueConstants {

    public static final String CLIENT_REGISTRATION_QUEUE = "clientRegistration";
    public static final String CLIENT_SEND_QUEUE = "clientSend";
    private static final String CLIENT_RECEIVE_QUEUE = "clientReceive";

    private QueueConstants() {
        throw new UnsupportedOperationException(format("%s should not be instantiated", QueueConstants.class.getSimpleName()));
    }

}