package PictureReader.view;

import PictureReader.MainApp;
import javafx.fxml.FXML;

/**
 * Created by adriansalas on 16/02/2016.
 */
public class WindowController {

    // Reference to the main application.
    private MainApp mainApp;

    @FXML
    private void openDir() {
        System.out.println("open directory");
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
    }

    @FXML
    private void changeToEn() {
        System.out.println("change to english");
    }

    @FXML
    private void changeToRu() {
        System.out.println("change to russian");
    }

    @FXML
    private void about() {
        System.out.println("about");
    }
}
