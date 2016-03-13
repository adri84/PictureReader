package PictureReader.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.Locale;
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

    public Label welcomeMess;
    public Label chooseLang;
    public Button chooseDirB;
    public Button exitB;


    @FXML
    public void changeLang() {
        if (radio_fr.isSelected()) {
            mainApp.setLanguage("fr",false);
            changeLanguage("fr");
        } else if (radio_en.isSelected()) {
            mainApp.setLanguage("en",false);
            changeLanguage("en");

        } else {
            mainApp.setLanguage("ru",false);
            changeLanguage("ru");

        }
    }


    @FXML
    private void openDir() {
        System.out.println("open directory");
        mainApp.loadControllers();
        mainApp.initRootLayout();
        mainApp.dataOverviewController.activateInputs(true);
    }

    public void changeLanguage(String l) {
        ResourceBundle r = ResourceBundle.getBundle("PictureReader.bundles.NodeNames",new Locale(l));

        welcomeMess.setText(r.getString("firstwindow.label.welcome"));
        chooseLang.setText(r.getString("firstwindow.label.chooseLang"));
        chooseDirB.setText(r.getString("firstwindow.button.chooseDir"));
        exitB.setText(r.getString("firstwindow.button.exit"));

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
