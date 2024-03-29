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
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static javafx.application.Platform.runLater;

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
        refreshPeriodically();
    }

    @Override
    public void onInternalCorrespondenceEvent(Correspondence correspondence) {
        runLater(() -> listViewInternalCorrespondence.getItems().add(correspondence));
    }

    @Override
    public void onExternalCorrespondenceEvent(Correspondence correspondence) {
        runLater(() -> listViewExternalCorrespondence.getItems().add(correspondence));
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

    private void refreshPeriodically() {
        Runnable runnable = () -> {
            listViewInternalCorrespondence.refresh();
            listViewExternalCorrespondence.refresh();
        };

        newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 0, 10, SECONDS);
    }

}
