package PictureReader.view;

import PictureReader.MainApp;
import javafx.fxml.FXML;

import java.util.Locale;

/**
 * Created by adriansalas on 16/02/2016.
 */
public class WindowController {

    private MainApp mainApp;

    @FXML
    private void openDir() {

        System.out.println("open directory");
        mainApp.changeImagePath();


    }


    public WindowController(MainApp s) {
        this.mainApp = s;
    }


    @FXML
    private void saveas() {
        System.out.println("save as");
    }

    @FXML
    private void closeWindow() {
        System.exit(1);
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
        mainApp.setLanguage(new Locale("fr"));
    }

    @FXML
    private void changeToEn() {
        System.out.println("change to english");
        mainApp.setLanguage(new Locale("en"));
    }

    @FXML
    private void changeToRu() {
        System.out.println("change to russian");
        mainApp.setLanguage(new Locale("ru"));
    }

    @FXML
    private void about() {
        System.out.println("about");
    }
}
