package nl.mailsystem.server.ui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import nl.mailsystem.common.ui.BaseMain;
import nl.mailsystem.server.ServerController;

import java.io.IOException;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
public class Main extends BaseMain {

    private static final String FXML_FILE = "Server.fxml";
    private static final String STAGE_TITLE = "Server";

    private static ServerController serverController;

    public static void main(String[] args) {
        setPosition(args);
        setController(args);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE));
        loader.setControllerFactory(c -> new ServerFxmlController(serverController));

        String title = format("%s - %s", STAGE_TITLE, serverController.getDomain());
        initStage(stage, loader, title);
    }

    private static void setController(String[] args) {
        serverController = new ServerController(args[0]);
    }

}
