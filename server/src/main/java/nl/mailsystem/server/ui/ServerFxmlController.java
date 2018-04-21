package nl.mailsystem.server.ui;

import javafx.fxml.Initializable;
import lombok.Setter;
import nl.mailsystem.server.ServerGateway;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Robin Laugs
 */
public class ServerFxmlController implements Initializable {

    @Setter
    private ServerGateway gateway;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
