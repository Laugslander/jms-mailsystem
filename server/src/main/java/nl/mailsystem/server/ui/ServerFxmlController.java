package nl.mailsystem.server.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.mailsystem.common.domain.Correspondence;
import nl.mailsystem.common.domain.MailDomain;
import nl.mailsystem.common.ui.listener.ExternalCorrespondenceEventListener;
import nl.mailsystem.common.ui.listener.InternalCorrespondenceEventListener;
import nl.mailsystem.server.ServerController;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
public class ServerFxmlController implements Initializable, InternalCorrespondenceEventListener, ExternalCorrespondenceEventListener {

    @FXML
    private Label labelInternalCorrespondence, labelExternalCorrespondence;

    @FXML
    private ListView<Correspondence> listViewInternalCorrespondence, listViewExternalCorrespondence;

    private final ServerController controller;

    ServerFxmlController(ServerController controller) {
        this.controller = controller;

        setListeners();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLabels();
    }

    @Override
    public void onInternalCorrespondenceEvent(Correspondence correspondence) {
        listViewInternalCorrespondence.getItems().add(correspondence);
    }

    @Override
    public void onExternalCorrespondenceEvent(Correspondence correspondence) {
        listViewExternalCorrespondence.getItems().add(correspondence);
    }

    private void setListeners() {
        this.controller.setInternalCorrespondenceEventListener(this);
        this.controller.setExternalCorrespondenceEventListener(this);
    }

    private void setLabels() {
        MailDomain domain = controller.getDomain();

        labelInternalCorrespondence.setText(format("Internal mails - %s", domain));
        labelExternalCorrespondence.setText(format("External mails - %s", domain.getTop()));
    }

}
