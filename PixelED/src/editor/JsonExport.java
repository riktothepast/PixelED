package editor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import NodoImagen.NodoImg;

public class JsonExport {

	public JsonExport(){
		
	}
	
	public void writeFile(int[][] tiles, ArrayList<NodoImg> imagenes, String path){
		System.out.println(path);
		String salida;
		salida = "\"Platforms\": [";
		for(int x=0;x<tiles.length;x++){
			for(int y=0;y<tiles[0].length;y++){
				if(tiles[x][y]!=-1){
					salida += "{\"x\" : "+x+", ";
					salida += "\"y\" : "+y+", ";
					salida += "\"image\" : \""+imagenes.get(tiles[x][y]).getName().replace(".png", "")+"\" ";
					salida += "},";
				}
			}
		}
		
		salida += "]";
		System.out.println(salida);
		
		 PrintWriter printWriter = null;
	        try {
	            // Will overwrite the file if exists or creates new
	            printWriter = new PrintWriter(path+"export.json",
	                    "UTF-8");
	            printWriter.println(salida);
	        } catch (FileNotFoundException fileNotFoundException) {
	            fileNotFoundException.printStackTrace();
	        } catch (UnsupportedEncodingException unsupportedEncodingException) {
	            unsupportedEncodingException.printStackTrace();
	        } finally {
	            printWriter.close();
	        }
	}
	
	
}
