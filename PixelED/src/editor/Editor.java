package editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

import NodoImagen.NodoImg;

public class Editor extends JPanel implements KeyListener, MouseListener{

	private Graphics dbg;
	private Image dbImage = null;
	public int tiles[][]=new int[20][13];
	int w,h,tS;
	boolean bigChunk;
	int currentTileX, currentTileY;
	int anchorX, anchorY;
	int imgIndex;
	ArrayList<NodoImg> imagenes;
	String path;
	
	private static final long serialVersionUID = 1L;

	public Editor(int width, int height, int tileSize, File file){
		path = file.getAbsolutePath();
		this.h = height;
		this.w = width;
		this.tS = tileSize;
		tiles=new int[w][h];
		currentTileX = 0;
		currentTileY = 0;
		imgIndex = 0;
		imagenes = new ImagesLoader().getImagesFromDir(file);
		
		for(int x=0;x<tiles.length;x++){
			for(int y=0;y<tiles[0].length;y++){
				tiles[x][y]=-1;
			}
		}
		
	}
	
	
	public void paint(Graphics g) {
  		update(g);	
 	}
	
 	public void update (Graphics g){
 		
 		dbImage = createImage(getWidth(), getHeight());
		 dbg=dbImage.getGraphics();
		 dbg.setColor(Color.black);
		 dbg.fillRect(0, 0, getWidth(), getHeight());	
		 dbg.setColor(Color.GREEN);
		 
			for(int x=0;x<tiles.length;x++){
				for(int y=0;y<tiles[0].length;y++){
					if(tiles[x][y]!=-1)
						 dbg.drawImage(imagenes.get(tiles[x][y]).getImage(),x*tS,y*tS,tS,tS, null);
				}
			}
			
		 for(int x=0;x<tiles.length;x++){
				for(int y=0;y<tiles[0].length+1;y++){
					dbg.drawLine(x*tS, y*tS, w*tS, y*tS);
					dbg.drawLine(x*tS, y*tS, x*tS, h*tS);
				}
		}
		 

 		
		 dbg.setColor(Color.RED);
		 if(!bigChunk){
			dbg.drawRect(currentTileX*tS, currentTileY*tS, tS, tS);
		 }else{
			dbg.drawRect(anchorX*tS,anchorY*tS,currentTileX*tS, currentTileY*tS);
		 }
		
		 if(imagenes.size()>0){
			 for (int x =0; x < imagenes.size(); x++)
				 dbg.drawImage(imagenes.get(x).getImage(),x*tS, (tiles[0].length+1)*tS,tS,tS, null);
		 }
		 
			dbg.drawRect(imgIndex*tS,  (tiles[0].length+1)*tS, tS, tS);
		 
		 g.drawImage(dbImage, 0, 0, null);
		 repaint();
		 
 	}
	
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		if(arg0.getKeyChar()=='w'){
			if(currentTileY>0)
				currentTileY -=1;
		}
		
		if(arg0.getKeyChar()=='a'){
			if(currentTileX>0)
				currentTileX -=1;
		}
		
		if(arg0.getKeyChar()=='s'){
			if(currentTileY<tiles[0].length-1)
				currentTileY +=1;
		}
		
		if(arg0.getKeyChar()=='d'){
			if(currentTileX<tiles.length-1)
				currentTileX +=1;
		}
		
		if(arg0.getKeyChar()==' '){
			bigChunk = true;
			System.out.println("space");
			anchorX = currentTileX;
			anchorY = currentTileY;
		}
		
		if(arg0.getKeyCode()==40){
			tiles[currentTileX][currentTileY]=imgIndex;
		}
		
		if(arg0.getKeyCode()==38){
			tiles[currentTileX][currentTileY]=-1;
		}
		
		if(arg0.getKeyCode()==KeyEvent.VK_LEFT){
			if(imgIndex>0)
				imgIndex--;
		}
		
		if(arg0.getKeyCode()==KeyEvent.VK_RIGHT){
			if(imgIndex<imagenes.size()-1)
				imgIndex++;
		}
		
		if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
			new JsonExport().writeFile(tiles, imagenes, path);
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyChar()==' '){
			bigChunk = false;
			anchorX = currentTileX;
			anchorY = currentTileY;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	
}
