package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MyMenu extends MenuBar {
	
	Menu file;
	
	MenuItem exit, save, load;
	
	DroneArena arena;
	SimView simView;
	MyCanvas canvas;
	
	public MyMenu(SimView simView, DroneArena arena, MyCanvas canvas) {		
		
		this.arena = arena;
		this.simView = simView;
		this.canvas = canvas;
		
		createSave();
		createLoad();
		createExit();
		createFile();
		
	}
	
	/**
	 * Adds File option to menu bar, to store save, load and exit
	 */
	private void createFile() {
		
		file = new Menu("File");	
		file.getItems().addAll(exit, save, load);
		this.getMenus().add(file);
		
	}
	
/**
 * Create save option, to save drone arena
 */
private void createSave() {
		
		
		save = new MenuItem("Save");
		
		save.setOnAction(e -> {
			
			// Set a filter of a file type to save
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File f) {
					if (f.getAbsolutePath().endsWith(".bin")) return true;
					if (f.isDirectory()) return true;
					return false;
				}

				@Override
				public String getDescription() {
					return "bin";
				}
			};

			// Saves arena state
			try {
				JFileChooser chooser = new JFileChooser("droneSaves"); // Instantiate, open at designated folder
				chooser.setFileFilter(filter); // Assign filter
				int returnVal = chooser.showSaveDialog(null); // returns what is chosen
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File selFile = chooser.getSelectedFile();
					ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(selFile));
					os.writeObject(arena.getArena());
					os.close();
				}
			} catch (IOException f) {
				f.printStackTrace();
			}
			
			try {
				
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("droneSaves/saving.bin"));
				
				out.writeObject(arena);
				out.close();
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});	
	
}

/**
 * Create load option, to load drone arena
 */
private void createLoad() {
	
	load = new MenuItem("Load");
	
	load.setOnAction(e -> {
		
		// Set a filter of a file type to save
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.getAbsolutePath().endsWith(".bin")) return true;
				if (f.isDirectory()) return true;
				return false;
			}
			@Override
			public String getDescription() {
				return "bin";
			}
		};

		try {
			JFileChooser chooser = new JFileChooser("droneSaves");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File selFile = chooser.getSelectedFile();
				if(selFile.isFile()){
					ObjectInputStream is = new ObjectInputStream(new FileInputStream(selFile));
					DroneArena temp = (DroneArena)is.readObject();
					is.close();						
					
					// Transfer from loaded object
					arena.setDrones(temp.getDrones());
					arena.drawArena(canvas);
					
				}
			}
		} catch (IOException err) {
			err.printStackTrace();
		} catch (ClassNotFoundException err) {
			err.printStackTrace();
		}
		
		
		});
	
}
	
	/**
	 * Create exit option, to exit program
	 */
	private void createExit() {
		
		exit = new MenuItem("Exit");
		
		exit.setOnAction(e -> {
			
			System.exit(0);
			
		});
		
	}	

}
