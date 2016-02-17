package PictureReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Image> imageData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PictureReader");

        initRootLayout(new Locale("en", "EN"));


        showImageOverview("/Users/adriansalas/Pictures/SpacePics/");
    }

    public MainApp() {
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout(Locale locale) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            loader.setResources(ResourceBundle.getBundle("PictureReader/ButtonsNames", locale));
            rootLayout = (BorderPane) loader.load();


            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showImageOverview(String path) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ImageOverview.fxml"));
            ScrollPane imageOverview = (ScrollPane) loader.load();

            imageOverview.setContent(setTileContent(path));

            // Set image overview into the center of root layout.
            rootLayout.setCenter(imageOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLanguage() {
        //loadView()
    }


    public TilePane setTileContent(String path) {

        TilePane tile = new TilePane();
        tile.setPadding(new Insets(1,1,1,1));
        tile.setHgap(1);
        tile.setPrefColumns(3);

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            tile.getChildren().addAll(imageView);
        }

        return tile;

    }


    private ImageView createImageView(final File imageFile) {
        // DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
        // The last two arguments are: preserveRatio, and use smooth (slower)
        // resizing

        ImageView imageView = null;
        try {
            final javafx.scene.image.Image image = new javafx.scene.image.Image(new FileInputStream(imageFile), 150, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
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
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }


    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Image> getImageData() {
        return imageData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
