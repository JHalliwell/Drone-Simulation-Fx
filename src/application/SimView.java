package application;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimView extends VBox{	
	
	private Stage simStage;
	private Scene simScene;
	private Canvas simCanvas;
	private GraphicsContext simGc;
	private AnchorPane simPane;
	
	private DroneArena droneArena;
	private ArenaSimulation arenaSim;
	private ArenaGrid arenaGrid;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 750;
	
	public SimView() {
		
		simStage = new Stage();
		simStage.setTitle("DRONE SIMULATOR");
		simStage.setResizable(false);	
		
		simPane = new AnchorPane();
		simPane.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);				

		createButtons();
		createArena();			
		
		simScene = new Scene(simPane);
		simStage.setScene(simScene);
		
	}	
	
	/**
	 * Creates drone arena and adds to pane, then draws it
	 */
	public void createArena() {		
		
		droneArena = new DroneArena();
		simPane.getChildren().add(droneArena.getArenaCanvas());
		droneArena.drawArena();
		
	}
	
	/**
	 * Creates buttons and adds to pane
	 */
	public void createButtons() {
		
		Button addButton = new Button();
		addButton.setText("Add Drone");
		addButton.setLayoutX(600);
		addButton.setLayoutY(50);
		addButton.setPrefSize(100, 20);	
		
		addButton.setOnAction(e -> {
			for (int i = 0; i < 200; i++) {
				droneArena.addDrone();
				droneArena.drawArena();	
			}
			
				droneArena.addDrone();
				droneArena.drawArena();	
			
			
		});
		
		Button playButton = new Button();
		playButton.setText("Move Drones");
		playButton.setLayoutX(600);
		playButton.setLayoutY(150);
		playButton.setOnAction(e -> {		
			
			
		});
		
		Button stopButton = new Button();
		stopButton.setText("Stop");
		stopButton.setLayoutX(600);
		stopButton.setLayoutY(100);
		stopButton.setPrefSize(100, 20);	
		stopButton.setOnAction(e -> {
			
		});
		
		simPane.getChildren().addAll(addButton, stopButton, playButton);		
		
	}
	
	/**
	 * 
	 * @return SimStage
	 */
	public Stage getSimStage() {
		
		return simStage;
		
	}
}