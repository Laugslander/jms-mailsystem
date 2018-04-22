package nl.mailsystem.server.ui;

import javafx.fxml.Initializable;
import lombok.Setter;
import nl.mailsystem.server.ServerController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Robin Laugs
 */
public class ServerFxmlController implements Initializable {

    @Setter
    private ServerController controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
