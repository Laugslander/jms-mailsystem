package nl.mailsystem.client.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.fxml.FXMLLoader.load;

/**
 * @author Robin Laugs
 */
public class Main extends Application {

    private static final String FXML_FILE = "Client.fxml";
    private static final String SCENE_TITLE = "Client";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(load(getClass().getResource(FXML_FILE))));
        stage.setTitle(SCENE_TITLE);
        stage.show();
    }

}
