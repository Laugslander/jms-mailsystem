package nl.mailsystem.common.ui;

import javafx.application.Application;

/**
 * @author Robin Laugs
 */
public abstract class BaseMain extends Application {

    protected static double stagePositionX;
    protected static double stagePositionY;

    protected static void setPosition(String[] args) {
        stagePositionX = Double.valueOf(args[1]);
        stagePositionY = Double.valueOf(args[2]);
    }

}
