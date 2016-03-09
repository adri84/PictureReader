package PictureReader.controller;

import PictureReader.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;

/**
 * Created by adriansalas on 16/02/2016.
 */
public class WindowController {

    private MainApp mainApp;

    @FXML
    private TextField txt;

    @FXML
    private void openDir() {
        System.out.println("open directory");
        mainApp.createMetadata();
        mainApp.changeImagePath();
    }


    public WindowController(MainApp s) {
        this.mainApp = s;
    }


    @FXML
    private void saveas() {
        System.out.println("save as");
        mainApp.createMetadata();
    }

    @FXML
    private void closeWindow() {
        System.exit(1);
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void rename() {
        System.out.println("rename");
    }


    @FXML
    private void addTag() {
        System.out.println("add tag");
    }

    @FXML
    private void modTag() {
        System.out.println("modify tag");
    }



    @FXML
    private void changeToFr() {

        System.out.println("change to french");
        mainApp.setLanguage("fr");
    }

    @FXML
    private void changeToEn() {
        System.out.println("change to english");
        mainApp.setLanguage("en");    }

    @FXML
    private void changeToRu() {
        System.out.println("change to russian");
        mainApp.setLanguage("ru");
    }

    @FXML
    private void about() {
        System.out.println("about");
    }


}
