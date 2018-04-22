package nl.mailsystem.common.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Robin Laugs
 */
public abstract class BaseMain extends Application {

    private static double stagePositionX;
    private static double stagePositionY;

    protected static void setPosition(String[] args) {
        stagePositionX = Double.valueOf(args[1]);
        stagePositionY = Double.valueOf(args[2]);
    }

    protected void initStage(Stage stage, FXMLLoader loader, String title) throws IOException {
        stage.setScene(new Scene(loader.load()));
        stage.setTitle(title);
        stage.setX(stagePositionX);
        stage.setY(stagePositionY);
        stage.show();
    }

}
