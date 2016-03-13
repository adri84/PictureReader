package PictureReader.controller;

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
    public final AppController mainApp;
    final ObservableList<String> listItems;
    public Image currentImage;

    //Composants
    public TextField nameText;
    public TextField inputTextTag;
    public Button saveImageButton;
    public Button addTagButton;
    public ListView<String> listBoxMain;
    public String listViewInitText;


    public ImageDataOverviewController(AppController mainApp) {
        this.mainApp = mainApp;
        listItems = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();
        listViewInitText = resources.getString("listview.text");

        if (currentImage == null) {
            listItems.clear();
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

                clearDataOverview();

            }
        }
    }

    public void clearDataOverview() {
        if (nameText != null && inputTextTag != null && listItems != null) {
            currentImage = null;
            nameText.clear();
            inputTextTag.clear();
            listItems.clear();
        }

        if (listItems != null) {
            listItems.add(listViewInitText);
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

            listBoxMain.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {

                KeyCode keyPressed = keyEvent.getCode();

                switch (keyPressed) {
                    case DELETE:
                        //currentImage.getTags().remove(cell.getItem());
                        //listBoxMain.getItems().remove(cell.getItem());
                        break;
                    case BACK_SPACE:
                        //currentImage.getTags().remove(cell.getItem());
                        //listBoxMain.getItems().remove(cell.getItem());
                        break;
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

        ResourceBundle r = ResourceBundle.getBundle("PictureReader.bundles.helpTooltips",mainApp.getMainLocale());

        if (displayTooltips == true) {
            Tooltip.install(nameText, new Tooltip(r.getString("image.label.text.desc")));
            Tooltip.install(saveImageButton, new Tooltip(r.getString("image.button.save.name.desc")));
            Tooltip.install(listBoxMain, new Tooltip(r.getString("listview.text.desc")));
            Tooltip.install(inputTextTag, new Tooltip(r.getString("image.text.add.tag.desc")));
            Tooltip.install(addTagButton, new Tooltip(r.getString("image.button.add.tag.desc")));

        }
        else {
            Tooltip.uninstall(nameText, new Tooltip(r.getString("image.label.text.desc")));
            Tooltip.uninstall(saveImageButton, new Tooltip(r.getString("image.button.save.name.desc")));
            Tooltip.uninstall(listBoxMain, new Tooltip(r.getString("listview.text.desc")));
            Tooltip.uninstall(inputTextTag, new Tooltip(r.getString("image.text.add.tag.desc")));
            Tooltip.uninstall(addTagButton, new Tooltip(r.getString("image.button.add.tag.desc")));
        }

    }
}
