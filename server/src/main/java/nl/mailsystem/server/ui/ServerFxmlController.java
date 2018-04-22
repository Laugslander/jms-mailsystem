package nl.mailsystem.server.ui;

import javafx.fxml.Initializable;
import nl.mailsystem.server.ServerController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Robin Laugs
 */
public class ServerFxmlController implements Initializable {

    private final ServerController controller;

    ServerFxmlController(ServerController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
