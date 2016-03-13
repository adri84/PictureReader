package PictureReader.controller;

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

    private AppController mainApp;
    private ResourceBundle resource;

    public TextField tagsSearchInput;
    public Button tagsSearchButton;
    public Button tagsSearchReset;
    public Label labelResults;

    public WindowController(AppController s) {
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
            setLabelText(ResourceBundle.getBundle("PictureReader.bundles.NodeNames",mainApp.getMainLocale()).getString("search.result.empty"));
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

        ResourceBundle r = ResourceBundle.getBundle("PictureReader.bundles.NodeNames",mainApp.getMainLocale());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(r.getString("about.title"));
        alert.setHeaderText(r.getString("about.header"));
        alert.setContentText(r.getString("about.content"));
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
        ResourceBundle r = ResourceBundle.getBundle("PictureReader.bundles.helpTooltips",mainApp.getMainLocale());
        if (displayTooltips == true) {
            Tooltip.install(tagsSearchInput, new Tooltip(r.getString("search.text.desc")));
            Tooltip.install(tagsSearchButton, new Tooltip(r.getString("button.search.desc")));
            Tooltip.install(tagsSearchReset, new Tooltip(r.getString("button.reset.desc")));
            Tooltip.install(labelResults, new Tooltip(r.getString("search.result.desc")));
        }
        else {
            Tooltip.uninstall(tagsSearchInput, new Tooltip(r.getString("search.text.desc")));
            Tooltip.uninstall(tagsSearchButton, new Tooltip(r.getString("button.search.desc")));
            Tooltip.uninstall(tagsSearchReset, new Tooltip(r.getString("button.reset.desc")));
            Tooltip.uninstall(labelResults, new Tooltip(r.getString("search.result.desc")));
        }
    }

    public void setLabelText(String text) {
        this.labelResults.setText(text);
    }

}
