package nl.mailsystem.router.ui;

import javafx.fxml.Initializable;
import nl.mailsystem.router.RouterController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Robin Laugs
 */
public class RouterFxmlController implements Initializable {

    private RouterController controller;

    RouterFxmlController(RouterController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
