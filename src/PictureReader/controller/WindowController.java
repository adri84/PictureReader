package PictureReader.controller;

import PictureReader.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adriansalas on 16/02/2016.
 */
public class WindowController implements Initializable{

    private MainApp mainApp;
    private ResourceBundle resource;

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
            setLabelText("Vous devez entrer des mots clés");
        }

    }

    @FXML
    public void resetTags() {
        try {
            mainApp.backToMainView(resource.getString("search.reset"));
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
        mainApp.setLanguage("fr",true);
    }

    @FXML
    private void changeToEn() {
        System.out.println("change to english");
        mainApp.setLanguage("en",true);    }

    @FXML
    private void changeToRu() {
        System.out.println("change to russian");
        mainApp.setLanguage("ru",true);
    }

    @FXML
    private void about() {
        System.out.println("about");
    }

    public void setLabelText(String text) {
        this.labelResults.setText(text);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        labelResults.setText(resources.getString("search.text"));
    }

}
