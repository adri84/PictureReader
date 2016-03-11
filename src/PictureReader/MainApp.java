package PictureReader;

import PictureReader.controller.FirstWindowController;
import PictureReader.controller.ImageDataOverviewController;
import PictureReader.controller.WindowController;
import PictureReader.model.Image;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainApp extends Application {

    public Stage primaryStage;
    public BorderPane rootLayout;
    public Scene scene;
    public ScrollPane imageOverview;
    public Pane imageDataView;
    public Locale mainLocale;
    public WindowController windowController;
    public ImageDataOverviewController dataOverviewC;
    public Image selectedImage;
    public String currentPath;
    boolean metadataCreated = false;


    private ObservableList<Image> imageData = FXCollections.observableArrayList();
    private ObservableList<Image> tmpImageData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PictureReader");

        mainLocale = new Locale("en");
        displayFistWindow();
    }

    public void displayFistWindow() {
        try {


            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/FirstWindow.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale));


            FirstWindowController firstWController = new FirstWindowController(this);
            fxmlLoader.setController(firstWController);

            BorderPane firstWindow = fxmlLoader.load();

            // Show the scene containing the root layout.
            Scene s = new Scene(firstWindow);
            primaryStage.setScene(s);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {


            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setResources(ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale));
            fxmlLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

            windowController = new WindowController(this);
            fxmlLoader.setController(windowController);

            rootLayout = fxmlLoader.load();

            changeImagePath();
            showDataOverview();


            rootLayout.setCenter(imageOverview);
            rootLayout.setRight(imageDataView);

            selectedImage = null;


            // Show the scene containing the root layout.
            scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadRootLayout(Locale locale) throws IOException {

        mainLocale = null;
        mainLocale = locale;


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setResources(ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale));
        fxmlLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));


        windowController = new WindowController(this);
        fxmlLoader.setController(windowController);

        showDataOverview();


        rootLayout = fxmlLoader.load();
        rootLayout.setCenter(imageOverview);
        rootLayout.setRight(imageDataView);

        selectedImage = null;

        scene = new Scene(rootLayout);


        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void showImageOverview(String path) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ImageOverview.fxml"));

            imageOverview = loader.load();
            currentPath = path;
            imageOverview.setContent(setTileContent(path));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSearchImageOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ImageOverview.fxml"));

            imageOverview = loader.load();
            imageOverview.setContent(setTileSearchContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

        while (!metadataCreated) {
            metadataCreated = createMetadata();
        }
        System.out.println("Stage is closing");
    }

    public void showDataOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ImageDataOverview.fxml"));
            loader.setResources(ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale));

            dataOverviewC = new ImageDataOverviewController(this);
            loader.setController(dataOverviewC);

            imageDataView = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLanguage(String locale, boolean reload) {

        try {
            this.mainLocale = new Locale(locale);

            if (reload) {
                this.reloadRootLayout(mainLocale);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean changeImagePath() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {

            imageData.clear();
            this.showImageOverview(selectedDirectory.getAbsolutePath());
            rootLayout.setCenter(imageOverview);
            return true;
        }

        return false;
    }

    public TilePane setTileContent(String path) {

        TilePane tile = new TilePane();
        tile.setPadding(new Insets(1, 1, 1, 1));
        tile.setHgap(1);
        tile.setPrefColumns(3);


        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        File metadataFolder = new File(path + "/.metadata/");

        boolean metadataIsSet = false;
        String tagsPaths;

        if (metadataFolder.exists()) {
            metadataIsSet = true;
        }


        assert listOfFiles != null;
        for (final File file : listOfFiles) {

            String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());

            if (extension.equals("jpg") || extension.equals("png")) {

                Image img =new Image(createImageView(file), file.getPath(), file.getName());

                if (metadataIsSet) {
                    tagsPaths = metadataFolder.getAbsolutePath() + "/" + file.getName().substring(0, file.getName().lastIndexOf('.'));
                    img.setTags(loadImageTags(tagsPaths));
                }

                imageData.add(img);
                tile.getChildren().addAll(img.imageView);
            }

        }

        if (!metadataIsSet) {
            createMetadata();
        }

        return tile;
    }

    public TilePane setTileSearchContent() {

        TilePane tile = new TilePane();
        tile.setPadding(new Insets(30, 30, 30, 30));
        tile.setHgap(5);
        tile.setPrefColumns(3);

        for (Image img : tmpImageData) {

            try {
                tile.getChildren().addAll(img.imageView);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }

        }

        return tile;
    }

    private ImageView createImageView(final File imageFile) {

        ImageView imageView = null;


        try {
            FileInputStream f = new FileInputStream(imageFile);
            final javafx.scene.image.Image image = new javafx.scene.image.Image(f, 150, 0, true, true);
            imageView = new ImageView(image);
            f.close();
            imageView.setFitWidth(150);
            imageView.setOnMouseClicked(mouseEvent -> {
                try {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                        if (mouseEvent.getClickCount() == 1) {
                            fillImageData(imageFile);
                        }

                        if (mouseEvent.getClickCount() == 2) {
                            displayFullscreenImage(imageFile);
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageView;
    }

    public Image getImagePositionInArray(String path) {
        int pos = -1;
        for (int i = 0; i < imageData.size(); i++) {
            if (imageData.get(i).getImagePath().equals(path)) {
                pos = i;
            }
        }
        return imageData.get(pos);
    }

    public ArrayList<String> loadImageTags(String path) {

        File tagsFile = new File(path);
        FileReader fileReader;
        int i;
        String tags = "";
        ArrayList<String> tagsArray = new ArrayList<>();

        try {
            fileReader = new FileReader(tagsFile);

            while ((i = fileReader.read()) != -1) {
                tags += (char) i;
            }

            String[] splittedTags = tags.split("\n");

            for (String splittedTag : splittedTags) {
                if (!splittedTag.equals("")) {
                    tagsArray.add(splittedTag);
                }
            }

            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tagsArray;
    }

    public void displayFullscreenImage(File imageFile) throws FileNotFoundException {

        BorderPane borderPane = new BorderPane();
        ImageView imageView = new ImageView();
        javafx.scene.image.Image image = new javafx.scene.image.Image(new FileInputStream(imageFile));
        imageView.setImage(image);
        imageView.setStyle("-fx-background-color: BLACK");
        imageView.setFitHeight(primaryStage.getHeight() - 10);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        imageView.setCache(true);
        borderPane.setCenter(imageView);
        borderPane.setStyle("-fx-background-color: BLACK");

        Stage newStage = new Stage();
        newStage.setWidth(primaryStage.getWidth());
        newStage.setHeight(primaryStage.getHeight());
        newStage.setTitle(imageFile.getName());
        Scene scene = new Scene(borderPane, Color.BLACK);
        newStage.setScene(scene);
        newStage.show();
    }

    public void fillImageData(File imageFile) throws FileNotFoundException {

        if (selectedImage != null && getImagePositionInArray(imageFile.getPath()) != selectedImage) {
            selectedImage.imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.0), 0, 0, 0, 0);");
        }

        selectedImage = getImagePositionInArray(imageFile.getPath());
        selectedImage.imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");


        if (selectedImage != null) {

            String toParseName = selectedImage.getImageName();
            dataOverviewC.activateInputs(false);
            dataOverviewC.setNameText(toParseName.substring(0, toParseName.lastIndexOf('.')));
            dataOverviewC.setTagText(ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale).getString("image.text.add.tag"));
            dataOverviewC.setTags(selectedImage.tags);
            dataOverviewC.setCurrentImage(selectedImage);
        } else {
            dataOverviewC.activateInputs(true);
        }


    }

    public boolean createMetadata() {

        File dir = new File(currentPath + "/.metadata");

        dir.mkdir();

        FileWriter fw;

        for (Image anImageData : imageData) {

            String tmp = currentPath + "/.metadata/" + anImageData.getImageName();
            String path = tmp.substring(0, tmp.lastIndexOf('.'));

            try {
                fw = new FileWriter(path);
                String str = "";

                for (int j = 0; j < anImageData.getTags().size(); j++) {
                    str += anImageData.getTags().get(j) + "\n";
                }

                fw.write(str);
                fw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }

        return true;
    }

    public void saveImageMetadata(Image modImage) {

        FileWriter fw;
        String path = currentPath + "/.metadata/" + modImage.getImageName().substring(0, modImage.getImageName().lastIndexOf('.'));

        ArrayList<String> tags = modImage.getTags();

        try {

            fw = new FileWriter(path);
            String str = "";

            for (String tag : tags) {
                str += tag + "\n";
            }

            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void searchByTags(String[] searchTags) throws IOException {


        ObservableList<Image> toDisplay = FXCollections.observableArrayList();
        ObservableList<Image> tmp = FXCollections.observableArrayList(imageData);

        for (String searchTag : searchTags) {
            for (Image aTmp : tmp) {
                aTmp.getTags().stream().filter(tag -> tag.equals(searchTag)).filter(tag -> !toDisplay.contains(aTmp)).forEach(tag -> toDisplay.add(aTmp));
            }
        }


        if (toDisplay.size() != 0) {
            setTmpImageData(toDisplay);
            showSearchImageOverview();
            reloadRootLayout(mainLocale);
            if (toDisplay.size() == 1) {
                ResourceBundle r = ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale);
                windowController.setLabelText(r.getString("search.result.found") + " " + toDisplay.size() + " " + r.getString("search.result.one"));
            } else {
                ResourceBundle r = ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale);
                windowController.setLabelText(r.getString("search.result.found") + " " + toDisplay.size() + " " + r.getString("search.result.many"));
            }

        } else {
            setTmpImageData(toDisplay);
            showSearchImageOverview();
            String label = ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale).getString("search.result.null");
            reloadRootLayout(mainLocale);
            windowController.setLabelText(label);
        }
    }

    public void backToMainView(String resultat) throws IOException {
        setTmpImageData(imageData);
        showSearchImageOverview();
        reloadRootLayout(mainLocale);
        windowController.setLabelText(resultat);
    }

    public void renameImage(Image currentImage, String newName) {

        String extension = currentImage.getImagePath().substring(currentImage.getImagePath().lastIndexOf(".") + 1, currentImage.getImagePath().length());

        File oldMetadataFile = new File(currentPath + "/.metadata/" + currentImage.getImageName().substring(0, currentImage.getImageName().lastIndexOf('.')));
        File newMetadataFile = new File(currentPath + "/.metadata/" + newName);

        File oldImage = new File(currentPath + "/" + currentImage.getImageName());
        File newImage = new File(currentPath + "/" + newName + "." + extension);


        try {
            FileUtils.moveFile(oldMetadataFile, newMetadataFile);
            FileUtils.moveFile(oldImage, newImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentImage.setImageName(newName);
        currentImage.setImagePath(currentPath + "/" + newName);
        currentImage.setImageView(createImageView(newImage));


        imageData.clear();
        selectedImage.imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.0), 0, 0, 0, 0);");
        selectedImage = null;

        this.showImageOverview(currentPath);
        rootLayout.setCenter(imageOverview);

    }

    public void setTmpImageData(ObservableList<Image> tmpImageData) {
        this.tmpImageData = FXCollections.observableArrayList(tmpImageData);
    }
}
