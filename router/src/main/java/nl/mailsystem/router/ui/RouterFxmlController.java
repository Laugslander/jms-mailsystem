package nl.mailsystem.router.ui;

import javafx.fxml.Initializable;
import lombok.Setter;
import nl.mailsystem.router.RouterGateway;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Robin Laugs
 */
public class RouterFxmlController implements Initializable {

    @Setter
    private RouterGateway gateway;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
