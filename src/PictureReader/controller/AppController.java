package PictureReader.controller;

import javafx.stage.Stage;

/**
 * Created by adriansalas on 12/03/2016.
 */
public class AppController {

    public Stage primaryStage;


    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PictureReader");
    }
}
