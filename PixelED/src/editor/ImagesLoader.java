package editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import NodoImagen.NodoImg;

public class ImagesLoader {


    static final String[] EXTENSIONS = new String[]{"png"
    };
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    public ImagesLoader(){
    	
    }
    
    public ArrayList<NodoImg> getImagesFromDir(File file){
    	ArrayList<NodoImg> imagenes = new ArrayList<NodoImg>();
    	 if (file.isDirectory()) { // make sure it's a directory
             for (final File f : file.listFiles(IMAGE_FILTER)) {
                 BufferedImage img = null;

                 try {
                     img = ImageIO.read(f);
                     // you probably want something more involved here
                     // to display in your UI
                     imagenes.add(new NodoImg(img,f.getName() ));
                 } catch (final IOException e) {
                     // handle errors here
                	
                 }
             }
         }
    	return imagenes;
    }


}