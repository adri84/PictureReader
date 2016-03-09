package PictureReader.controller;

import PictureReader.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Created by adriansalas on 16/02/2016.
 */
public class WindowController {

    private MainApp mainApp;

    @FXML
    public javafx.scene.control.TextField tagsSearchInput;

    @FXML
    public javafx.scene.control.Button tagsSearchButton;

    @FXML
    public javafx.scene.control.Button tagsSearchReset;

    @FXML
    public javafx.scene.control.Label labelResults;


    @FXML
    private void openDir() {
        System.out.println("open directory");
        mainApp.createMetadata();
        mainApp.changeImagePath();
    }

    @FXML
    public void searchTags() {

        if (!tagsSearchInput.getText().equals("")) {
            try {
                mainApp.searchByTags(tagsSearchInput.getText().split(";"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Veuillez selectionner un fichier !");
        }

    }

    @FXML
    public void resetTags() {
        try {
            mainApp.backToMainView();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
