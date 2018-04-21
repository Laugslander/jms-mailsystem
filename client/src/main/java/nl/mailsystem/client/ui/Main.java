package nl.mailsystem.client.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.mailsystem.client.ClientGateway;
import nl.mailsystem.common.ui.BaseMain;

/**
 * @author Robin Laugs
 */
public class Main extends BaseMain {

    private static final String FXML_FILE = "Client.fxml";
    private static final String STAGE_TITLE = "Client";

    private static ClientGateway gateway;

    public static void main(String[] args) {
        setPosition(args);
        setController(args);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE));
        Parent root = loader.load();
        ClientFxmlController controller = loader.getController();
        controller.setGateway(gateway);

        stage.setScene(new Scene(root));
        stage.setTitle(String.format("%s - %s", STAGE_TITLE, gateway.getAddress()));
        stage.setX(stagePositionX);
        stage.setY(stagePositionY);
        stage.setResizable(false);
        stage.show();
    }

    private static void setController(String[] args) {
        gateway = new ClientGateway(args[0]);
    }

}
