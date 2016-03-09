package PictureReader;

import PictureReader.controller.ImageDataOverviewController;
import PictureReader.controller.WindowController;
import PictureReader.model.Image;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
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
    public ImageDataOverviewController dataOverviewC;
    public Image selectedImage;
    public String currentPath;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Image> imageData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PictureReader");

        mainLocale = new Locale("en");
        initRootLayout();
    }


    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {


            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setResources(ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale));
            fxmlLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            WindowController c = new WindowController(this);
            fxmlLoader.setController(c);

            rootLayout = (BorderPane) fxmlLoader.load();

            changeImagePath();
            showDataOverview();

            rootLayout.setCenter(imageOverview);
            rootLayout.setRight(imageDataView);

            // Show the scene containing the root layout.
            scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadRootLayout(Locale locale) throws IOException {

        mainLocale =  null;
        mainLocale = locale;

        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setResources(ResourceBundle.getBundle("PictureReader.bundles.MyBundle", mainLocale));
        fxmlLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

        WindowController c = new WindowController(this);
        fxmlLoader.setController(c);

        showDataOverview();

        rootLayout = (BorderPane) fxmlLoader.load();
        rootLayout.setCenter(imageOverview);
        rootLayout.setRight(imageDataView);

        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void showImageOverview(String path) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ImageOverview.fxml"));

            imageOverview = (ScrollPane) loader.load();
            currentPath = path;
            imageOverview.setContent(setTileContent(path));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        createMetadata();
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

            imageDataView = (Pane) loader.load();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLanguage(String locale) {

        try {
            this.mainLocale = null;
            Locale test = new Locale(locale);
            this.mainLocale = test;
            this.reloadRootLayout(mainLocale);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeImagePath() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            imageData.clear();
            this.showImageOverview(selectedDirectory.getAbsolutePath());
            rootLayout.setCenter(imageOverview);
        }
    }

    public TilePane setTileContent(String path) {

        TilePane tile = new TilePane();
        tile.setPadding(new Insets(1,1,1,1));
        tile.setHgap(1);
        tile.setPrefColumns(3);



        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        File metadataFolder = new File(path+"/metadata/");

        boolean metadataIsSet = false;
        String tagsPaths = "";

        if (metadataFolder.exists()) {
            metadataIsSet = true;
        }


        for (final File file : listOfFiles) {

            String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());

            if(extension.equals("jpg") || extension.equals("png")) {

                Image img = new Image(createImageView(file),file.getPath(),file.getName());

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

    private ImageView createImageView(final File imageFile) {

        ImageView imageView = null;


        try {

            final javafx.scene.image.Image image = new javafx.scene.image.Image(new FileInputStream(imageFile), 150, 0, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                            if(mouseEvent.getClickCount() == 1){
                                fillImageData(imageFile);
                            }

                            if(mouseEvent.getClickCount() == 2){
                                displayFullscreenImage(imageFile);
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return imageView;
    }

    public Image getImagePositionInArray(String path) {
        int pos = -1;
        for (int i = 0; i < imageData.size() ; i++) {
            if (imageData.get(i).getImagePath().equals(path)) {
                pos = i;
            }
        }
        return imageData.get(pos);
    }

    public ArrayList<String> loadImageTags(String path) {

            File tagsFile = new File(path);
            FileReader fileReader;
            int i = 0;
            String tags = "";
            ArrayList<String> tagsArray = new ArrayList<String>();

            try {
                fileReader = new FileReader(tagsFile);

                while((i = fileReader.read()) != -1) {
                    tags += (char)i;
                }

                String[] splittedTags = tags.split("\n");

                for (int j = 0; j < splittedTags.length; j++) {
                    if (!splittedTags[j].equals("")) {
                        tagsArray.add(splittedTags[j]);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
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

        if(selectedImage != null && getImagePositionInArray(imageFile.getPath()) != selectedImage) {
            selectedImage.imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.0), 0, 0, 0, 0);");
        }

        selectedImage = getImagePositionInArray(imageFile.getPath());
        selectedImage.imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");


        if(selectedImage != null) {

            String toParseName = selectedImage.getImageName();
            dataOverviewC.setNameText(toParseName.substring(0, toParseName.lastIndexOf('.')));
            dataOverviewC.setTags(selectedImage.tags);
            dataOverviewC.setCurrentImage(selectedImage);
        }



    }

    public void createMetadata() {

        File dir = new File (currentPath+"/metadata");
        dir.mkdirs();

        FileWriter fw;

        for (int i = 0; i <imageData.size() ; i++) {

            String tmp = currentPath+"/metadata/"+imageData.get(i).getImageName();

            String path = tmp.substring(0, tmp.lastIndexOf('.'));

            try {
                fw = new FileWriter(path);
                String str = "";

                for (int j = 0; j < imageData.get(i).getTags().size(); j++) {
                    str += imageData.get(i).getTags().get(j)+"\n";
                }

                fw.write(str);
                fw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void searchByTags() {
        // chercher par tags
    }

    public void renameImage(Image currentImage, String newName) {

        String extension = currentImage.getImagePath().substring(currentImage.getImagePath().lastIndexOf(".") + 1, currentImage.getImagePath().length());


        File oldMetadataFile = new File(currentPath + "/metadata/" + currentImage.getImageName().substring(0, currentImage.getImageName().lastIndexOf('.')));
        File newMetadataFile = new File(currentPath + "/metadata/" + newName);

        File oldImage = new File(currentPath + "/" + currentImage.getImageName());
        File newImage = new File(currentPath + "/" + newName + "." + extension);

        try {
            Files.move(new File(currentPath + "/" + currentImage.getImageName()).toPath(), new File(currentPath + "/" + newName + "." + extension).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            Files.move(new File(currentPath + "/metadata/" + currentImage.getImageName().substring(0, currentImage.getImageName().lastIndexOf('.'))).toPath(), new File(currentPath + "/metadata/" + newName).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.out.print("Marche pas");
        }

        //oldMetadataFile.renameTo(newMetadataFile);

        currentImage.setImageName(newName);
        currentImage.setImagePath(currentPath + "/" + newName);
        currentImage.setImageView(createImageView(newImage));


        imageData.clear();
        this.showImageOverview(currentPath);
        rootLayout.setCenter(imageOverview);




    }


    public boolean renameFile(File oldFile, File newFile) {

        if(oldFile.renameTo(newFile)){
            return true;
        }else{
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
