package NodoImagen;
import java.awt.image.BufferedImage;

public class NodoImg {
	BufferedImage imagen;
	String name;
	
	public NodoImg(BufferedImage img, String n){
		imagen = img;
		name = n;
	}
	
	public String getName(){
		return name;
	}
	
	public BufferedImage getImage(){
		return imagen;
	}
	
}
