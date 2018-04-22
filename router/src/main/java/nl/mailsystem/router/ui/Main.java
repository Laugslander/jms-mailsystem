package nl.mailsystem.router.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.mailsystem.common.ui.BaseMain;
import nl.mailsystem.router.RouterController;

/**
 * @author Robin Laugs
 */
public class Main extends BaseMain {

    private static final String FXML_FILE = "Router.fxml";
    private static final String STAGE_TITLE = "Router";

    private static RouterController routerController;

    public static void main(String[] args) {
        setPosition(args);
        setController(args);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE));
        Parent root = loader.load();
        RouterFxmlController fxmlController = loader.getController();
        fxmlController.setController(routerController);

        stage.setScene(new Scene(root));
        stage.setTitle(String.format("%s - %s", STAGE_TITLE, routerController.getTop()));
        stage.setX(stagePositionX);
        stage.setY(stagePositionY);
        stage.show();
    }

    private static void setController(String[] args) {
        routerController = new RouterController(args[0]);
    }

}
