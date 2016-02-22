package PictureReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import PictureReader.model.Image;
import PictureReader.view.WindowController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.BuilderFactory;


public class MainApp extends Application {

    public Stage primaryStage;
    public BorderPane rootLayout;
    public Scene scene;
    public WindowController controller;
    public ScrollPane imageOverview;
    public Locale mainLocale;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Image> imageData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PictureReader");

        mainLocale = new Locale("fr");
        initRootLayout();
    }


    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {


            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setResources(ResourceBundle.getBundle("PictureReader.bundles.MyBundle", parseLocale()));
            fxmlLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            WindowController c = new WindowController(this);
            fxmlLoader.setController(c);

            rootLayout = (BorderPane) fxmlLoader.load();

            changeImagePath();
            //showImageOverview("/Users/adriansalas/Pictures/SpacePics/");

            rootLayout.setCenter(imageOverview);

            // Show the scene containing the root layout.
            scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UTF8Control parseLocale() {
        UTF8Control utf8c = new UTF8Control();
        utf8c.setL(mainLocale);
        return utf8c;
    }

    public void reloadRootLayout(Locale locale) throws IOException {


        mainLocale =  null;
        mainLocale = locale;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setResources(ResourceBundle.getBundle("PictureReader.bundles.MyBundle", parseLocale()));
        fxmlLoader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

        WindowController c = new WindowController(this);
        fxmlLoader.setController(c);

        rootLayout = (BorderPane) fxmlLoader.load();
        rootLayout.setCenter(imageOverview);

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
            imageOverview.setContent(setTileContent(path));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLanguage(Locale locale) {

        try {
            this.mainLocale = locale;
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
            System.out.print(selectedDirectory.getAbsolutePath());
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

        for (final File file : listOfFiles) {

            String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());

            if(extension.equals("jpg") || extension.equals("png")) {
                Image img = new Image(createImageView(file),file.getName(),file.getPath());
                imageData.add(img);
                tile.getChildren().addAll(img.imageView);
            }

        }

        return tile;

    }


    private ImageView createImageView(final File imageFile) {

        ImageView imageView = null;
        try {
            final javafx.scene.image.Image image = new javafx.scene.image.Image(new FileInputStream(imageFile), 150, 0, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            final ImageView finalImageView = imageView;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                        if(mouseEvent.getClickCount() == 2){
                            try {
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
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }

                        if(mouseEvent.getClickCount() == 1){

                            finalImageView.setStyle("-fx-focus-color: BLACK");
                            //textField.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ;");

                                System.out.println(imageFile.getName());


                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }



    public ObservableList<Image> getImageData() {
        return imageData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
