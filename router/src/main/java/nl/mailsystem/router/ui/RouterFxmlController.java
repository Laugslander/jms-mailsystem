package nl.mailsystem.router.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import nl.mailsystem.common.domain.Correspondence;
import nl.mailsystem.router.RouterController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Robin Laugs
 */
public class RouterFxmlController implements Initializable {

    @FXML
    private ListView<Correspondence> listViewInternalCorrespondence, listViewExternalCorrespondence;

    RouterFxmlController(RouterController controller) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
