package nl.mailsystem.server.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.mailsystem.common.ui.BaseMain;
import nl.mailsystem.server.ServerController;

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
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE));
        Parent root = loader.load();
        ServerFxmlController fxmlController = loader.getController();
        fxmlController.setController(serverController);

        stage.setScene(new Scene(root));
        stage.setTitle(String.format("%s - %s", STAGE_TITLE, serverController.getDomain()));
        stage.setX(stagePositionX);
        stage.setY(stagePositionY);
        stage.show();
    }

    private static void setController(String[] args) {
        serverController = new ServerController(args[0]);
    }

}
