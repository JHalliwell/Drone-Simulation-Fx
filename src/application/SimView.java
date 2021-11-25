package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimView extends VBox{	
	
	private Stage simStage;
	private Scene simScene;
	private BorderPane simPane;
	private StackPane simStackPane;	
	private Group canvasRoot, statusRoot;
	private Canvas canvas;
	private MyCanvas simCanvas;
	private ScrollPane scrollPaneDrone, scrollPaneObstacle;
	private VBox droneBox, obstacleBox, infoBox;
	private TextField droneText, obstacleText;
	
	private MyMenu simMenu;
	private Buttons buttons;
	
	private DroneArena arena;

	private static final int WINDOW_WIDTH = 1500;
	private static final int WINDOW_HEIGHT = 900;
	public static final int ARENA_WIDTH = 1200;
	public static final int ARENA_HEIGHT = 800;
	
	public SimView() {
				
		// Initialise Stage 		
		simStage = new Stage();
		simStage.setTitle("DRONE SIMULATOR 29020945");
		simStage.setResizable(false);				
		
		// Initialise Border Pane
		simPane = new BorderPane();
		simPane.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// Creating Canvas
		canvasRoot = new Group();		
		simStackPane = new StackPane();
		simStackPane.setStyle("-fx-background-color: #7fb5d4");		
		canvas = new Canvas(ARENA_WIDTH, ARENA_HEIGHT);		
		simStackPane.getChildren().add(canvas);
		canvasRoot.getChildren().add(simStackPane);
		
		// Create custom canvas object
		simCanvas = new MyCanvas(canvas.getGraphicsContext2D(), ARENA_WIDTH, ARENA_HEIGHT);
		
		// Create status area
//		infoBox = new VBox();
//		scrollPaneObstacle = new ScrollPane();
//		scrollPaneDrone = new ScrollPane();
//		droneBox = new VBox();
//		obstacleBox = new VBox();				
//		scrollPaneDrone.setContent(droneBox);
//		scrollPaneObstacle.setContent(obstacleBox);
//		infoBox.getChildren().addAll(scrollPaneDrone, scrollPaneObstacle);
		
		
//		
//		SplitPane statusPane = new SplitPane();
//		droneText = new TextField();
//		obstacleText = new TextField();
//		statusPane.setOrientation(Orientation.VERTICAL);
//		statusPane.setResizableWithParent(simPane, true);
//		statusPane.getItems().addAll(droneText, obstacleText);
			
		// Create drone arena
		arena = new DroneArena();	
		
		// Add key event handlers
		createKeyEvents();
						
		// Initialise menu, buttons
		simMenu = new MyMenu(this, arena, simCanvas);
		buttons = new Buttons(arena, simCanvas, canvas);
			
		// Add to border pane: menu, buttons
		simPane.setTop(simMenu);
		simPane.setBottom(buttons);	
		simPane.setLeft(canvasRoot);	
		//simPane.setRight();
		
		// Start animation timer for status
		startAnimationTimer();
		
		// Initialise scene and add pane, set scene to stage
		simScene = new Scene(simPane);
		simStage.setScene(simScene);

	}	
	
	public void createKeyEvents() {
		
		simPane.setOnKeyPressed(e -> {
			
			switch (e.getCode()) {
			case A: arena.translatePlacementWall(0); break;
			case D : arena.translatePlacementWall(1); break;
			case W : arena.translatePlacementWall(2); break;
			case S : arena.translatePlacementWall(3); 
			}
			
		});
		
	}
	
	public void drawStatus() {
		
		
		
	}
	
	private void startAnimationTimer() {
		
		AnimationTimer at = new AnimationTimer() {
			
			@Override
            public void handle(long now)
            {
            	drawStatus();
            }	
			
		};
		
		at.start();
	}
	

	/**
	 * 
	 * @return SimStage
	 */
	public Stage getSimStage() {
		
		return simStage;
		
	}
	
}
	
