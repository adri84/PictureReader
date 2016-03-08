package PictureReader.model;

import java.time.LocalDate;
import java.util.ArrayList;

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
    public ArrayList<String> tags;

    /**
     * Default constructor.
     */
    public Image() {
        this(null,null,null);
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
        this.tags = new ArrayList<String>();

    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public String getImageName() {
        return imageName.get();
    }

    public void setImageName(String imageName) {
        this.imageName.set(imageName);
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }







}
