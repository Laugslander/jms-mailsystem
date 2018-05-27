package nl.mailsystem.common.messaging;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
public final class MessagingConstants {

    /**
     * Queues
     */

    public static final String CLIENT_SERVER_REGISTRATION_QUEUE = "client_server_registration";
    public static final String SERVER_ROUTER_REGISTRATION_QUEUE = "server_router_registration";

    public static final String CLIENT_SERVER_MAIL_QUEUE = "client_server_mail";
    public static final String SERVER_CLIENT_MAIL_QUEUE = "server_client_mail";
    public static final String SERVER_ROUTER_MAIL_QUEUE = "server_router_mail";
    public static final String ROUTER_SERVER_MAIL_QUEUE = "router_server_mail";
    public static final String ROUTER_ROUTER_MAIL_QUEUE = "router_router_mail";

    /**
     * Topics
     */

    public static final String SERVER_CLIENT_MAIL_TOPIC = "server_client_mail";

    /**
     * Properties
     */

    public static final String STRING_PROPERTY_CLASS_NAME = "class_name";

    private MessagingConstants() {
        throw new UnsupportedOperationException(format("%s should not be instantiated",
                MessagingConstants.class.getSimpleName()));
    }

}