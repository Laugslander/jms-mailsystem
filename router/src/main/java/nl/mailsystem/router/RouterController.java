package nl.mailsystem.router;

import lombok.Getter;
import lombok.extern.java.Log;

import static java.lang.String.format;
import static java.util.logging.Level.INFO;

/**
 * @author Robin Laugs
 */
@Log
public class RouterController {

    @Getter
    private String top;

    public RouterController(String top) {
        this.top = top;

        log.log(INFO, format("Server with domain %s initialized", top));
    }

}
