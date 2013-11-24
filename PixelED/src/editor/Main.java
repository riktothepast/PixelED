package editor;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class Main {

	public static void main(String args[]){

		final JFileChooser chooser = new JFileChooser() {
			private static final long serialVersionUID = 1L;

			public void approveSelection() {
                if (getSelectedFile().isDirectory()) {
                	JFrame jframe=new JFrame();
                	jframe.remove(this);
            		jframe.setTitle("PixelED");
            		jframe.setSize(1024, 768);
            		Editor scr= new Editor(25,16,24, getSelectedFile());
            		jframe.add(scr);
            		jframe.setVisible(true);
            		jframe.addKeyListener(scr);
            		
                    return;
                } 
            }
		};
		chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		JFrame jframe=new JFrame();

		jframe.setTitle("Select a directory with your sprites");
		jframe.setSize(320,480);
		jframe.add(chooser);
		jframe.setVisible(true);
	}
}