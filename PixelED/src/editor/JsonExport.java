package editor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import NodoImagen.NodoImg;
import NodoObjects.NodoObj;

public class JsonExport {

	public JsonExport(){
		
	}
	
	public void writeFile(int[][] tiles, ArrayList<NodoImg> imagenes,ArrayList<NodoObj> jugadores,ArrayList<NodoObj> items, NodoObj orb, String path){
		System.out.println(path);
		String salida;
        salida = "{";
        salida += "\"metadata\": { \"name\" : \"1\", ";

        salida += "\"orbX\" :" +orb.x*1.0+", ";
        salida += "\"orbY\" :" +orb.y*1.0+" ";

        salida += "}," ;
        //players
        salida += "\"PlayerSpawn\": [";

        for(int x=0; x<jugadores.size();x++)     {
            salida += "{\"x\" : "+jugadores.get(x).x*1.0+", ";
            salida += "\"y\" : "+jugadores.get(x).y*1.0+" ";
            if(x>=jugadores.size()-1)
                salida += "}";
            else
                salida += "},";
        }

        salida += "],";


        //items
        salida += "\"ItemSpawn\": [";
        for(int x=0; x<items.size();x++)     {
            salida += "{\"x\" : "+items.get(x).x*1.0+", ";
            salida += "\"y\" : "+items.get(x).y*1.0+" ";
            if(x>=items.size()-1)
                salida += "}";
            else
                salida += "},";
        }
        salida += "],";

        //platforms
		salida += "\"Platforms\": [";
		for(int x=0;x<tiles.length;x++){
			for(int y=0;y<tiles[0].length;y++){
				if(tiles[x][y]!=-1){
					salida += "{\"x\" : "+x*1.0+", ";
					salida += "\"y\" : "+y*1.0+", ";
					salida += "\"image\" : \""+imagenes.get(tiles[x][y]).getName().replace(".png", "")+"\" ";

                        salida += "},";
				}
			}
		}
       salida = salida.substring(0,salida.length()-1);
		
		salida += "]";
        salida += "}";
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
