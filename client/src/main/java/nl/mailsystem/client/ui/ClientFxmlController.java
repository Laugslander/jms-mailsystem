package nl.mailsystem.client.ui;

import javafx.fxml.Initializable;
import lombok.Setter;
import nl.mailsystem.client.ClientGateway;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Robin Laugs
 */
public class ClientFxmlController implements Initializable {

    @Setter
    private ClientGateway gateway;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
