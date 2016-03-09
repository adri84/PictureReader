package PictureReader.controller;

import PictureReader.MainApp;
import PictureReader.model.Image;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ImageDataOverviewController implements Initializable {

    private final MainApp mainApp;

    @FXML
    public javafx.scene.control.TextField nameText;

    @FXML
    public javafx.scene.control.TextField inputTextTag;

    @FXML
    private ListView<String> listBoxMain;

    final ObservableList<String> listItems = FXCollections.observableArrayList("Cliquez sur une image");

    public Image currentImage;


    public ImageDataOverviewController(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void setNameText(String s) {
        nameText.setText(s);
    }

    @FXML
    public void setTags(ArrayList<String> tags) {
        listItems.clear();

        if(tags.size() != 0) {
            for (int i = 0; i < tags.size(); i++) {
                listItems.add(tags.get(i));
            }
        }
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    @FXML
    public void addTag() {
        if(!inputTextTag.getText().equals("")) {
            listItems.add(inputTextTag.getText());
            saveTagChanges();
            inputTextTag.clear();
        }
    }

    public void saveTagChanges() {

        ObservableList<String> tags = listBoxMain.getItems();
        ArrayList<String> tagsList = new ArrayList<String>();

        for (int i = 0; i < tags.size() ; i++) {
            if(!tags.get(i).equals("")) {
                tagsList.add(tags.get(i));
            }
        }
        this.currentImage.setTags(tagsList);
    }

    public void saveNameChanges() {
        if(!nameText.getText().equals("")) {
            mainApp.renameImage(this.currentImage, nameText.getText());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listBoxMain.setItems(listItems);

        listBoxMain.setCellFactory(TextFieldListCell.forListView());

        listBoxMain.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                listBoxMain.getItems().set(t.getIndex(), t.getNewValue());
                System.out.println("setOnEditCommit");
                saveTagChanges();
            }

        });

        listBoxMain.setOnEditCancel(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                System.out.println("setOnEditCancel");
            }
        });
    }
}
