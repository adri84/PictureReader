package sample;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Lister le contenu d'un répertoire
 * http://www.fobec.com/java/964/lister-fichiers-dossiers-repertoire.html
 * @author fobec 2010
 */
public class DiskFileExplorer {

    private String initialpath = "";
    private Boolean recursivePath = false;
    public int filecount = 0;
    public int dircount = 0;

    /**
     * Constructeur
     * @param path chemin du répertoire
     * @param subFolder analyse des sous dossiers
     */
    public DiskFileExplorer(String path, Boolean subFolder) {
        super();
        this.initialpath = path;
        this.recursivePath = subFolder;
    }

    public void list() {
        this.listDirectory(this.initialpath);
    }

    private void listDirectory(String dir) {

        String path = dir;

        JFrame f = new JFrame("Load Image Sample");

        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });




        File file = new File(dir);
        File[] files = file.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() == true) {
                    System.out.println("Dossier: " + files[i].getAbsolutePath());
                } else {
                    System.out.println("Fichier: " + files[i].getAbsolutePath());
                    f.add(new LoadImageApp(path+"/"+ files[i].getName()));
                }
                if (files[i].isDirectory() == true && this.recursivePath == true) {
                    this.listDirectory(files[i].getAbsolutePath());
                }
            }


            f.pack();
            f.setVisible(true);

        }
    }
}