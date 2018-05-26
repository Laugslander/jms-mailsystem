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
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static javafx.application.Platform.runLater;

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
        controller.setInternalCorrespondenceEventListener(this);
        controller.setExternalCorrespondenceEventListener(this);
    }

    private void setLabels() {
        labelInternalCorrespondence.setText(format("Internal mails - %s", controller.getTop()));
        labelExternalCorrespondence.setText("External mails");
    }

    private void refreshPeriodically() {
        Runnable runnable = () -> {
            listViewInternalCorrespondence.refresh();
            listViewExternalCorrespondence.refresh();
        };

        newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 0, 10, SECONDS);
    }

}
