package application;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimView extends VBox{	
	
	private Stage simStage;
	private Scene simScene;

	private BorderPane simPane;
	private HBox simHBox;

	
	private MyMenu simMenu;
	private Buttons buttons;
	
	private DroneArena arena;
	
	private AnimationTimer animationTimer;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 750;
	
	public SimView() {
				
		// Initialise Stage 		
		simStage = new Stage();
		simStage.setTitle("DRONE SIMULATOR");
		simStage.setResizable(false);		
		
		
		// Initialise Border Pane
		simPane = new BorderPane();
		simPane.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);	
		
		// Create drone arena
		createArena();
		
		// Initialise menu, buttons
		simMenu = new MyMenu();
		buttons = new Buttons(arena);
		
		// Add to border pane: menu, buttons
		simPane.setTop(simMenu);
		simPane.setBottom(buttons);		
		
		// Initialise scene and add pane, set scene to stage
		simScene = new Scene(simPane);
		simStage.setScene(simScene);
		
	}		
	
	private void createCursorCoords() {		
		
		Label mouseCoords = new Label();
		mouseCoords.setLayoutX(100);
		mouseCoords.setLayoutY(425);
		mouseCoords.setScaleX(1);
		mouseCoords.setScaleY(1);
		
		simPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
		        String msg =
		          ("x: " + event.getX() + ", y: " + event.getY());

		        mouseCoords.setText(msg);
		      }
		});
		
		simPane.getChildren().add(mouseCoords);
		
	}
	
	/**
	 * Creates drone arena and adds to pane, then draws it
	 */
	public void createArena() {		
		
		arena = new DroneArena();
		simPane.setCenter(arena.getArenaCanvas());
		arena.drawArena();		
		
	}	

	/**
	 * 
	 * @return SimStage
	 */
	public Stage getSimStage() {
		
		return simStage;
		
	}
}