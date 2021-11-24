package application;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
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
	private Canvas canvas;
	private MyCanvas simCanvas;
	private VBox rightPane;
	
	private MyMenu simMenu;
	private Buttons buttons;
	private Label label;
	
	private DroneArena arena;
	private AnimationTimer mainAnimation;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 750;
	public static final int ARENA_WIDTH = 700;
	public static final int ARENA_HEIGHT = 700;
	
	public SimView() {
				
		// Initialise Stage 		
		simStage = new Stage();
		simStage.setTitle("DRONE SIMULATOR 29020945");
		simStage.setResizable(false);				
		
		// Initialise Border Pane
		simPane = new BorderPane();
		simPane.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// Create group to store canvas
		Group root = new Group();
		
		// Create stackPane to hold canvas - for background
		simStackPane = new StackPane();
		simStackPane.setStyle("-fx-background-color: #7fb5d4");
		
		// Create canvas
		canvas = new Canvas(ARENA_WIDTH, ARENA_HEIGHT);	
		
		// Add canvas to stackPane, and stackPane to root
		simStackPane.getChildren().add(canvas);
		root.getChildren().add(simStackPane);
		
		// Create custom canvas object
		simCanvas = new MyCanvas(canvas.getGraphicsContext2D(), ARENA_WIDTH, ARENA_HEIGHT);
		
		// Create drone arena
		arena = new DroneArena();		
		
		simPane.setOnKeyPressed(e -> {
			
			switch (e.getCode()) {
			case A: arena.translatePlacementWall(0); break;
			case D : arena.translatePlacementWall(1); break;
			case W : arena.translatePlacementWall(2); break;
			case S : arena.translatePlacementWall(3); 
			}
			
		});
		
//		startAnimationTimer();
		
		rightPane = new VBox();
		rightPane.setAlignment(Pos.TOP_LEFT);		
		
		// Initialise menu, buttons
		simMenu = new MyMenu(this, arena, simCanvas);
		buttons = new Buttons(arena, simCanvas, canvas);
			
		// Add to border pane: menu, buttons
		simPane.setTop(simMenu);
		simPane.setBottom(buttons);	
		simPane.setCenter(root);	
		//simPane.setRight(rightPane);
		
		// Initialise scene and add pane, set scene to stage
		simScene = new Scene(simPane);
		simStage.setScene(simScene);
		
	}	
	
//	private void startAnimationTimer() {
//		
//		mainAnimation = new AnimationTimer()
//		{
//			@Override
//            public void handle(long now)
//            {
//				label = new Label();
//				label.setText(arena.drawStatus());
//				rightPane.getChildren().add(label);
//            	
//            }	
//		};
//		mainAnimation.start();
//		
//	}

	/**
	 * 
	 * @return SimStage
	 */
	public Stage getSimStage() {
		
		return simStage;
		
	}
	
}
	
