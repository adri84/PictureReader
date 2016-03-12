package PictureReader.controller;

import PictureReader.MainApp;
import PictureReader.model.Image;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ImageDataOverviewController implements Initializable {

    //Références
    public final MainApp mainApp;
    final ObservableList<String> listItems;
    public Image currentImage;

    //Composants
    public TextField nameText;
    public TextField inputTextTag;
    public Button saveImageButton;
    public Button addTagButton;
    public ListView<String> listBoxMain;
    public String listViewInitText;


    public ImageDataOverviewController(MainApp mainApp) {
        this.mainApp = mainApp;
        listItems = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();
        listViewInitText = resources.getString("listview.text");

        if (currentImage == null) {
            listItems.add(listViewInitText);
        }
    }

    public void setTags(ArrayList<String> tags) {
        listItems.clear();

        if (tags.size() != 0) {
            for (int i = 0; i < tags.size(); i++) {
                listItems.add(tags.get(i));
            }
        }
    }

    public void addTag() {
        if (!inputTextTag.getText().equals("")) {
            listItems.add(inputTextTag.getText());
            saveTagChanges();
            inputTextTag.clear();
        }
    }

    public void acceptNameInput() {
        if (currentImage == null) {
            nameText.getParent().requestFocus();
        }
    }

    public void acceptTagInput() {
        if (currentImage == null) {
            nameText.getParent().requestFocus();
        }
    }

    public void saveTagChanges() {

        if (currentImage != null) {
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
    }

    public void activateInputs(boolean bool) {

        nameText.setDisable(bool);
        inputTextTag.setDisable(bool);
        listBoxMain.setDisable(bool);
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

    private void initializeListView() {
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

            listBoxMain.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent keyEvent) {

                    KeyCode keyPressed = keyEvent.getCode();

                    switch (keyPressed) {
                        case DELETE:
                            currentImage.getTags().remove(cell.getItem());
                            listBoxMain.getItems().remove(cell.getItem());
                            break;
                        case BACK_SPACE:
                            currentImage.getTags().remove(cell.getItem());
                            listBoxMain.getItems().remove(cell.getItem());
                            break;
                    }
                }
            });


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

    public void setNameText(String s) {
        nameText.setText(s);
    }

    public void setTagText(String s) {
        inputTextTag.setPromptText(s);
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public void setTooltips(boolean displayTooltips) {

        ResourceBundle r = ResourceBundle.getBundle("PictureReader.bundles.helpTooltips");

        if (displayTooltips == true) {
            Tooltip.install(nameText, new Tooltip("test")); //This is for showing
            Tooltip.install(saveImageButton, new Tooltip("searchB")); //This is for showing
            Tooltip.install(listBoxMain, new Tooltip("test")); //This is for showing
            Tooltip.install(inputTextTag, new Tooltip("resetB")); //This is for showing
            Tooltip.install(addTagButton, new Tooltip("labelR")); //This is for showing

        }
        else {
            Tooltip.uninstall(nameText, new Tooltip("input")); //This is for showing
            Tooltip.uninstall(saveImageButton, new Tooltip("searchB")); //This is for showing
            Tooltip.uninstall(listBoxMain, new Tooltip("test")); //This is for showing
            Tooltip.uninstall(inputTextTag, new Tooltip("resetB")); //This is for showing
            Tooltip.uninstall(addTagButton, new Tooltip("labelR")); //This is for showing
        }

    }
}
