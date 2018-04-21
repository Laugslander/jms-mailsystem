package nl.mailsystem.router.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.mailsystem.common.ui.BaseMain;
import nl.mailsystem.router.RouterGateway;

/**
 * @author Robin Laugs
 */
public class Main extends BaseMain {

    private static final String FXML_FILE = "Router.fxml";
    private static final String STAGE_TITLE = "Router";

    private static RouterGateway gateway;

    public static void main(String[] args) {
        setPosition(args);
        setController(args);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE));
        Parent root = loader.load();
        RouterFxmlController controller = loader.getController();
        controller.setGateway(gateway);

        stage.setScene(new Scene(root));
        stage.setTitle(String.format("%s - %s", STAGE_TITLE, gateway.getTop()));
        stage.setX(stagePositionX);
        stage.setY(stagePositionY);
        stage.setResizable(false);
        stage.show();
    }

    private static void setController(String[] args) {
        gateway = new RouterGateway(args[0]);
    }

}
