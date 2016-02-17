package sample;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by adriansalas on 10/02/2016.
 */
public class ListFiles {
    /**
     * Exemple : lister les fichiers dans tous les sous-dossiers
     * @param args
     */
    public static void main(String[] args) {
        String pathToExplore = "/Users/adriansalas/Pictures/SpacePics";
        DiskFileExplorer diskFileExplorer = new DiskFileExplorer(pathToExplore, true);
        diskFileExplorer.list();


        /*
        Long start = System.currentTimeMillis();
        diskFileExplorer.list();
        System.out.println("----------");
        System.out.println("Analyse de " + pathToExplore + " en " + (System.currentTimeMillis() - start) + " mses");
        System.out.println(diskFileExplorer.dircount + " dossiers");
        System.out.println(diskFileExplorer.filecount + " fichiers");*/
    }
}
