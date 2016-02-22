package PictureReader.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

/**
 * Model class for an image.
 *
 * @author Adrian Salas
 */
public class Image {

    private final StringProperty imagePath;
    private final StringProperty imageName;
    public ImageView imageView;

    /**
     * Default constructor.
     */
    public Image() {
        this(null,null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param imagePath
     * @param imageName
     */
    public Image(ImageView imgView, String imagePath, String imageName) {
        this.imageView = imgView;
        this.imagePath = new SimpleStringProperty(imagePath);
        this.imageName = new SimpleStringProperty(imageName);

    }

    public String getImageName() {
        return imageName.get();
    }

    public StringProperty imageNameProperty() {
        return imageName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
