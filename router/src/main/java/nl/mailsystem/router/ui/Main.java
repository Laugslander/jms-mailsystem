package nl.mailsystem.router.ui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import nl.mailsystem.common.ui.BaseMain;
import nl.mailsystem.router.RouterController;

import java.io.IOException;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
public class Main extends BaseMain {

    private static final String FXML_FILE = "/fxml/Router.fxml";
    private static final String STAGE_TITLE = "Router";

    private static RouterController routerController;

    public static void main(String[] args) {
        setPosition(args);
        setController(args);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE));
        loader.setControllerFactory(c -> new RouterFxmlController(routerController));

        String title = format("%s - %s", STAGE_TITLE, routerController.getTop());
        initStage(stage, loader, title);
    }

    private static void setController(String[] args) {
        routerController = new RouterController(args[0]);
    }

}
