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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Class for everything on MenuBar
 * @author 29020945
 */
public class MyMenu extends MenuBar {
	
	private DroneArena arena;	
	private MyCanvas canvas;	
	private MenuItem exit, save, load, newArena, about;
	private Menu file, help;
	private SimView simView;
	private Stage simStage;
	
	// Objects for about section
	private Popup aboutPopup;
	private Label aboutLabel;
	private Image aboutImage;
	private ImageView aboutImageView;
	
	/**
	 * @param simView - Main simulation interface
	 * @param arena - Main drone and environment arena
	 * @param canvas - Canvas used for drawing onto arena
	 * @throws FileNotFoundException 
	 */
	public MyMenu(SimView simView, Stage simStage, DroneArena arena, MyCanvas canvas) throws FileNotFoundException {		
		
		this.arena = arena;
		this.simView = simView;
		this.canvas = canvas;
		this.simStage = simStage;
				
		createSave();
		createLoad();
		createNew();
		createExit();
		createFile();
		
		createAbout();
		createHelp();				
		
	}
	
	/**
	 * Create save option, to save drone arena
	 */
	private void createSave() {			
			
			save = new MenuItem("Save");
			
			save.setOnAction(e -> {
				
				// Set a filter to only allow .bin files
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
	
	this.setId("menu");
	
	load = new MenuItem("Load");
	
	load.setOnAction(e -> {
		
		// Set a filter to only load .bin files
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
					
					// Clear arena of drones and environment
					arena.clearDrones();	
					arena.clearEnvironment();
					Drone.droneCount = 0;
					
					// Add correct drones and environment from loaded arena object
					for (Drone d : temp.getDrones()) {						
						if (d instanceof RoamDrone) {
							arena.addDroneToList(d.getXPos(), d.getYPos(), d.getDirection(), canvas, 
													"roam");
						}
						if (d instanceof AttackDrone) {
							arena.addDroneToList(d.getXPos(), d.getYPos(), d.getDirection(), canvas, 
													"attack");
						}
						if (d instanceof CautiousDrone) {	
							arena.addDroneToList(d.getXPos(), d.getYPos(), d.getDirection(), canvas, 
													"cautious");
						}						
					}
					
					for (Environment en : temp.getEnvironment()) {						
						if (en instanceof Wall) {
							arena.addEnvironmentToList(en.getXPos(), en.getYPos(), en.getWidth(),
															en.getHeight(), "wall");
						}						
						if (en instanceof BlackHole) {
							arena.addEnvironmentToList(en.getXPos(), en.getYPos(), en.getWidth(), 
															en.getHeight(), "blackhole");
						}						
					}
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
	
	private void createNew() {		
		newArena = new MenuItem("New Arena");
		
		newArena.setOnAction(e -> {			
			arena.clearDrones();
			arena.clearEnvironment();
			arena.drawArena(canvas);			
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
	
	/**
	 * Adds File option to menu bar, and relevant menu items
	 */
	private void createFile() {
		
		file = new Menu("File");	
		file.getItems().addAll(exit, save, load, newArena);
		this.getMenus().add(file);
		
	}
	
	/**
	 * Create about option, to display helpful information
	 * @throws FileNotFoundException 
	 */
	private void createAbout() throws FileNotFoundException {
		
		about = new MenuItem("About");
		
		// Add About popup
		aboutPopup = new Popup();
		aboutPopup.setX(simView.getWindowWidth() * 0.092);
		aboutPopup.setY(simView.getWindowHeight() * 0.105);
		aboutPopup.setHideOnEscape(true);
		aboutPopup.setAutoHide(true);
		aboutImage = new Image(new FileInputStream("graphics/aboutthree.png"));
		aboutImageView = new ImageView(aboutImage);		
		aboutLabel = new Label();
		aboutLabel.setGraphic(aboutImageView);		
		aboutPopup.getContent().add(aboutLabel);
		
		about.setOnAction(e -> {
			
			aboutPopup.show(simStage);
			
		});
		
	}
	
	/**
	 * Creeat Help menuBar option
	 */
	private void createHelp() {
		
		help = new Menu("Help");
		help.getItems().add(about);
		this.getMenus().add(help);
		
	}
	


}
