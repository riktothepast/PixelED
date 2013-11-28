package editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

import NodoImagen.NodoImg;
import NodoObjects.NodoObj;

public class Editor extends JPanel implements KeyListener, MouseListener, MouseMotionListener{

	private Graphics dbg;
	private Image dbImage = null;
	public int tiles[][]=new int[20][13];
	int w,h,tS;
	boolean bigChunk;
	int currentTileX, currentTileY;
	int anchorX, anchorY;
	int imgIndex;
	boolean playerSpawn, itemSpawn, orbSpawn;
	ArrayList<NodoImg> imagenes;
    ArrayList<NodoObj>  jugadores;
    ArrayList<NodoObj>  items;
                 NodoObj orb;
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
		jugadores = new ArrayList<NodoObj>(4);
        items = new ArrayList<NodoObj>();

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

         dbg.setColor(Color.cyan);
         for(NodoObj nob : jugadores)                        {
             dbg.drawRect(nob.x*tS, nob.y*tS, tS, tS);
             dbg.drawLine(nob.x*tS, nob.y*tS, nob.x*tS+tS,nob.y*tS+ tS);
         }

        dbg.setColor(Color.yellow);
        for(NodoObj nob : items)                        {
            dbg.drawRect(nob.x*tS, nob.y*tS, tS, tS);
            dbg.drawLine(nob.x*tS, nob.y*tS, nob.x*tS+tS,nob.y*tS+ tS);
        }
        dbg.setColor(Color.magenta);
        if(orb!= null)                        {
            dbg.drawRect(orb.x*tS, orb.y*tS, tS, tS);
            dbg.drawLine(orb.x*tS, orb.y*tS, orb.x*tS+tS,orb.y*tS+ tS);
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

            dbg.setColor(Color.cyan);

        if(playerSpawn)
		    dbg.drawString("PlayerSpawn Mode", tS,(tiles[0].length+1)*tS+tS*2);

        if(itemSpawn)
            dbg.drawString("ItemSpawn Mode", tS,(tiles[0].length+1)*tS+tS*2);

        if(orbSpawn)
            dbg.drawString("OrbSpawn Mode", tS,(tiles[0].length+1)*tS+tS*2);

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
			anchorX = currentTileX;
			anchorY = currentTileY;
		}

        //spawn locations
        if(arg0.getKeyCode()==KeyEvent.VK_I){
            itemSpawn = itemSpawn ? false : true;
            orbSpawn = playerSpawn = false;
        }

        if(arg0.getKeyCode()==KeyEvent.VK_O){
            orbSpawn = orbSpawn ? false : true;
            itemSpawn = playerSpawn = false;
        }

        if(arg0.getKeyCode()==KeyEvent.VK_P){
            playerSpawn = playerSpawn ? false : true;
            itemSpawn = orbSpawn = false;
        }


		if(arg0.getKeyCode()==40){
            setItem(currentTileX, currentTileY,imgIndex );
        }
		
		if(arg0.getKeyCode()==38){
            removeItem(currentTileX,currentTileY);
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
            if(orb!=null&& jugadores.size()>0&&items.size()>0)
		    	new JsonExport().writeFile(tiles, imagenes,jugadores,items,orb, path);
            else
                System.out.println("you must place items, players and the orb.");
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
       if (arg0.getButton()== MouseEvent.BUTTON1){
           setItem(currentTileX, currentTileY,imgIndex );
       }

        if (arg0.getButton()== MouseEvent.BUTTON3||arg0.getButton()== MouseEvent.BUTTON2){
            removeItem(currentTileX,currentTileY);
        }
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

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        currentTileX = e.getX()/tS;
        currentTileY = (e.getY()-tS)/tS;

        if(currentTileX<0)
            currentTileX = 0;

        if(currentTileY<0)
            currentTileX = 0;

        if(currentTileY>tiles[0].length-1)
            currentTileY = tiles[0].length-1;

        if(currentTileX>tiles.length-1)
            currentTileX  = tiles.length-1;

    }


    void setItem(int currentTileX, int currentTileY, int imgIndex){
        if(playerSpawn)  {
            boolean exist =false;
            for(NodoObj nob : jugadores) {
                    if(nob.x==currentTileX&&nob.y==currentTileY)
                        exist = true;
            }
            if(!exist){
                if(jugadores.size()<4)
                  jugadores.add(new NodoObj(currentTileX,currentTileY));
            }
            return;
        }
        if(itemSpawn){
            boolean exist =false;
            for(NodoObj nob : items) {
                if(nob.x==currentTileX&&nob.y==currentTileY)
                    exist = true;
            }
            if(!exist){
                items.add(new NodoObj(currentTileX,currentTileY));
            }
            return;
        }
        if(orbSpawn){
            orb = new NodoObj(currentTileX,currentTileY);
              return;
        }
        tiles[currentTileX][currentTileY]=imgIndex;
    }

    void removeItem(int currentTileX, int currentTileY)
    {
        if(playerSpawn)  {
            for(NodoObj nob : jugadores) {
                if(nob.x==currentTileX&&nob.y==currentTileY)       {
                   jugadores.remove(nob);
                    break;                                          }
            }

            return;
        }
        if(itemSpawn){
            for(NodoObj nob : items) {
                if(nob.x==currentTileX&&nob.y==currentTileY)    {
                   items.remove(nob);
                    break;
                }
            }

            return;
        }
        if(orbSpawn){
                 orb = null;
            return;
        }
             tiles[currentTileX][currentTileY]=-1;
    }
}
