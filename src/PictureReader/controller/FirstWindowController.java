package PictureReader.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adriansalas on 16/02/2016.
 */
public class FirstWindowController implements Initializable{

    private AppController mainApp;

    @FXML
    public RadioButton radio_fr;

    @FXML
    public RadioButton radio_en;

    @FXML
    public RadioButton radio_ru;

    @FXML
    public void changeLang() {
        if (radio_fr.isSelected()) {
            mainApp.setLanguage("fr",false);
        } else if (radio_en.isSelected()) {
            mainApp.setLanguage("en",false);
        } else {
            mainApp.setLanguage("ru",false);
        }
    }


    @FXML
    private void openDir() {
        System.out.println("open directory");
        mainApp.loadControllers();
        mainApp.initRootLayout();
        mainApp.dataOverviewController.activateInputs(true);
    }


    public FirstWindowController(AppController s) {
        this.mainApp = s;
    }


    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radio_fr.setText(resources.getString("button.lang.fr"));
        radio_en.setText(resources.getString("button.lang.en"));
        radio_ru.setText(resources.getString("button.lang.ru"));

    }
}
