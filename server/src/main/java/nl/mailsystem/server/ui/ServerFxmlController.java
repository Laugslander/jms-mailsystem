package nl.mailsystem.server.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import nl.mailsystem.common.domain.Correspondence;
import nl.mailsystem.server.ServerController;
import nl.mailsystem.server.ui.listener.ExternalCorrespondenceEventListener;
import nl.mailsystem.server.ui.listener.InternalCorrespondenceEventListener;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Robin Laugs
 */
public class ServerFxmlController implements Initializable, InternalCorrespondenceEventListener, ExternalCorrespondenceEventListener {

    @FXML
    private ListView<Correspondence> listViewInternalCorrespondence, listViewExternalCorrespondence;

    ServerFxmlController(ServerController controller) {
        controller.setInternalCorrespondenceEventListener(this);
        controller.setExternalCorrespondenceEventListener(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void onInternalCorrespondenceEvent(Correspondence correspondence) {
        listViewInternalCorrespondence.getItems().add(correspondence);
    }

    @Override
    public void onExternalCorrespondenceEvent(Correspondence correspondence) {
        listViewExternalCorrespondence.getItems().add(correspondence);
    }

}
