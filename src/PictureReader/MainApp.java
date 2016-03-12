package PictureReader;

import PictureReader.controller.AppController;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {

    AppController myApp;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        myApp = new AppController(primaryStage);

    }


    @Override
    public void stop() {
        myApp.saveChanges();
    }


}
