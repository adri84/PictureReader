package PictureReader.controller;

import PictureReader.MainApp;
import PictureReader.model.Image;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

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

    final ObservableList<String> listItems;
    public Image currentImage;
    private ResourceBundle bundle;
    private String listViewInitText;


    public ImageDataOverviewController(MainApp mainApp) {
        this.mainApp = mainApp;
        listItems = FXCollections.observableArrayList();
    }

    @FXML
    public void setNameText(String s) {
        nameText.setText(s);
    }

    @FXML
    public void setTags(ArrayList<String> tags) {
        listItems.clear();

        if (tags.size() != 0) {
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
        if (!inputTextTag.getText().equals("")) {
            listItems.add(inputTextTag.getText());
            saveTagChanges();
            inputTextTag.clear();
        }
    }


    public void saveTagChanges() {

        ObservableList<String> tags = listBoxMain.getItems();
        ArrayList<String> tagsList = new ArrayList<String>();

        for (int i = 0; i < tags.size(); i++) {
            if (!tags.get(i).equals("")) {
                tagsList.add(tags.get(i));
            }
        }

        this.currentImage.setTags(tagsList);
        this.mainApp.saveImageMetadata(this.currentImage);
    }


    public void saveNameChanges() {

        if (!nameText.getText().equals("")) {
            String nameNoExt = currentImage.getImageName().substring(0, currentImage.getImageName().lastIndexOf('.'));

            if (!nameText.getText().equals(nameNoExt)) {
                mainApp.renameImage(this.currentImage, nameText.getText());

                currentImage = null;
                nameText.clear();
                inputTextTag.clear();
                listItems.clear();
                listItems.add(listViewInitText);
            } else {
                System.out.println("Même nom de fichier !");
                //alertbox erreur fichier?
            }
        } else {
            System.out.println("Veuillez selectionner un fichier !");
        }
    }

    public void initializeListView() {
        listBoxMain.setItems(listItems);

        listBoxMain.setCellFactory(lv -> {

            StringConverter<String> converter = new StringConverter<String>() {
                @Override
                public String toString(String object) {
                    return object;
                }

                @Override
                public String fromString(String string) {
                    return string;
                }
            };

            TextFieldListCell<String> cell = new TextFieldListCell<>(converter);
            ContextMenu contextMenu = new ContextMenu();

            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
            deleteItem.setOnAction(event -> {
                currentImage.getTags().remove(cell.getItem());
                listBoxMain.getItems().remove(cell.getItem());
            });
            contextMenu.getItems().addAll(deleteItem);

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });


        listBoxMain.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> t) {
                listBoxMain.getItems().set(t.getIndex(), t.getNewValue());
                System.out.println("setOnEditCommit");
                saveTagChanges();
            }

        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();

        listViewInitText = resources.getString("listview.text");
        listItems.add(listViewInitText);
    }
}
