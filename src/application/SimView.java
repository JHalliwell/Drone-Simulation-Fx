package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
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
	private VBox statusBox;
	private TextArea droneText;
	
	private MyMenu simMenu;
	private Buttons buttons;
	
	private DroneArena arena;

	private static final int WINDOW_WIDTH = 1400;
	private static final int WINDOW_HEIGHT = 900;
	public static final int ARENA_WIDTH = 1220;
	public static final int ARENA_HEIGHT = 818;
	
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
		scrollPaneDrone = new ScrollPane();
		scrollPaneDrone.setPrefWidth(170);
		scrollPaneDrone.setFitToWidth(true);
    	statusBox = new VBox();
    	
    	scrollPaneDrone.setContent(statusBox);
    	
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
		simPane.setRight(scrollPaneDrone);
		
		simPane.setPadding(new Insets(5));
		BorderPane.setMargin(buttons, new Insets(5));
		BorderPane.setMargin(scrollPaneDrone, new Insets(5));
		BorderPane.setMargin(canvasRoot, new Insets(5));
		
		drawStatus();
		
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
		
		statusBox.getChildren().clear();
		
		ArrayList<String> info = new ArrayList<String>();
		info = arena.describeAll();		

		for (String i : info) {
			
			Label l = new Label(i);
			statusBox.getChildren().add(l);
			
		}	
		
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
	
