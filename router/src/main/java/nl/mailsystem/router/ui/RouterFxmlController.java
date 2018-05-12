package nl.mailsystem.router.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.mailsystem.common.domain.Correspondence;
import nl.mailsystem.common.ui.listener.ExternalCorrespondenceEventListener;
import nl.mailsystem.common.ui.listener.InternalCorrespondenceEventListener;
import nl.mailsystem.router.RouterController;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
public class RouterFxmlController implements Initializable, InternalCorrespondenceEventListener, ExternalCorrespondenceEventListener {

    @FXML
    private Label labelInternalCorrespondence, labelExternalCorrespondence;

    @FXML
    private ListView<Correspondence> listViewInternalCorrespondence, listViewExternalCorrespondence;

    private final RouterController controller;

    RouterFxmlController(RouterController controller) {
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
        controller.setInternalCorrespondenceEventListener(this);
        controller.setExternalCorrespondenceEventListener(this);
    }

    private void setLabels() {
        labelInternalCorrespondence.setText(format("Internal mails - %s", controller.getTop()));
        labelExternalCorrespondence.setText("External mails");
    }

}
