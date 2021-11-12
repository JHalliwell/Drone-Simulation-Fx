package application;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MyMenu extends MenuBar {
	
	Menu file;
	
	MenuItem save, load, exit;
	
	public MyMenu() {		
		
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
		file.getItems().addAll(save, load, exit);
		this.getMenus().add(file);
		
	}
	
	/**
	 * Create save option, to save drone arena
	 */
	private void createSave() {
		
		save = new MenuItem("Save");
		
		save.setOnAction(e -> {
			
		});
		
	}
	
	/**
	 * Create load option, to load drone arena
	 */
	private void createLoad() {
		
		load = new MenuItem("Load");
		
		load.setOnAction(e -> {
			
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
