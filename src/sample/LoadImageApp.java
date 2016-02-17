package sample;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;
import javax.imageio.*;
import javax.swing.*;

/**
 * This class demonstrates how to load an Image from an external file
 */
public class LoadImageApp extends Component {

    BufferedImage img;

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public LoadImageApp(String path) {
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
        }

    }

    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(100,100);
        } else {
            return new Dimension(img.getWidth(null), img.getHeight(null));
        }
    }

}
