package PictureReader.controller;

import PictureReader.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adriansalas on 16/02/2016.
 */
public class WindowController implements Initializable{

    private MainApp mainApp;
    private ResourceBundle resource;

    public TextField tagsSearchInput;
    public Button tagsSearchButton;
    public Button tagsSearchReset;
    public Label labelResults;

    public WindowController(MainApp s) {
        this.mainApp = s;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        labelResults.setText(resources.getString("search.text.desc"));
    }

    public void openDir() {
        System.out.println("open directory");
        mainApp.createMetadata();
        mainApp.changeImagePath();
    }

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

    public void resetTags() {
        try {
            mainApp.backToMainView(resource.getString("search.reset"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveas() {
        System.out.println("save as");
        mainApp.createMetadata();
    }

    public void changeToFr() {

        System.out.println("change to french");
        mainApp.setLanguage("fr",true);
    }

    public void changeToEn() {
        System.out.println("change to english");
        mainApp.setLanguage("en",true);
    }

    public void changeToRu() {
        System.out.println("change to russian");
        mainApp.setLanguage("ru",true);
    }

    public void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();

        System.out.println("about");
    }

    public void helpMode() {
        mainApp.setDisplayTooltips();
        System.out.println("about");
    }

    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }


    public void setTooltips(boolean displayTooltips) {
        ResourceBundle r = ResourceBundle.getBundle("PictureReader.bundles.helpTooltips");
        if (displayTooltips == true) {
            Tooltip.install(tagsSearchInput, new Tooltip("test")); //This is for showing
            //Tooltip.install(tagsSearchInput, new Tooltip(r.getString("test"))); //This is for showing
            Tooltip.install(tagsSearchButton, new Tooltip("searchB")); //This is for showing
            Tooltip.install(tagsSearchReset, new Tooltip("resetB")); //This is for showing
            Tooltip.install(labelResults, new Tooltip("labelR")); //This is for showing
        }
        else {
            Tooltip.uninstall(tagsSearchInput, new Tooltip("input")); //This is for showing
            Tooltip.uninstall(tagsSearchButton, new Tooltip("searchB")); //This is for showing
            Tooltip.uninstall(tagsSearchReset, new Tooltip("resetB")); //This is for showing
            Tooltip.uninstall(labelResults, new Tooltip("labelR")); //This is for showing
        }
    }

    public void setLabelText(String text) {
        this.labelResults.setText(text);
    }
}
