package nl.mailsystem.router;

import lombok.Getter;

/**
 * @author Robin Laugs
 */
public class RouterController {

    @Getter
    private String top;

    public RouterController(String top) {
        this.top = top;
    }

}
