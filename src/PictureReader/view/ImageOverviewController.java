package PictureReader.view;

import PictureReader.MainApp;
import PictureReader.model.Image;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ImageOverviewController {
    @FXML
    private TableView<Image> imageTable;
    @FXML
    private TableColumn<Image, String> firstNameColumn;
    @FXML
    private TableColumn<Image, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ImageOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        //firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().pathProperty());
        //lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */

    /* inutile je pense

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        imageTable.setItems(mainApp.getImageData());
    }

    */
}