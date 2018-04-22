package nl.mailsystem.client.ui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import nl.mailsystem.client.ClientController;
import nl.mailsystem.common.ui.BaseMain;

import java.io.IOException;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
public class Main extends BaseMain {

    private static final String FXML_FILE = "/fxml/Client.fxml";
    private static final String STAGE_TITLE = "Client";

    private static ClientController clientController;

    public static void main(String[] args) {
        setPosition(args);
        setController(args);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE));
        loader.setControllerFactory(c -> new ClientFxmlController(clientController));

        String title = format("%s - %s", STAGE_TITLE, clientController.getAddress());
        initStage(stage, loader, title);
    }

    private static void setController(String[] args) {
        clientController = new ClientController(args[0]);
    }

}
