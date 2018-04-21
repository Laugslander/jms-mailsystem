package nl.mailsystem.router;

import lombok.Getter;

/**
 * @author Robin Laugs
 */
public class RouterGateway {

    @Getter
    private String top;

    public RouterGateway(String top) {
        this.top = top;
    }

}
