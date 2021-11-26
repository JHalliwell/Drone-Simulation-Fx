package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
	public static final int ARENA_HEIGHT = 818;
	public static final int ARENA_WIDTH = 1220;
	private static final int WINDOW_HEIGHT = 900;
	private static final int WINDOW_WIDTH = 1400;	
	private DroneArena arena;
	private Buttons buttons;
	private Canvas canvas;
	private Group canvasRoot, statusRoot;
	private TextArea droneText;
	private ScrollPane scrollPaneDrone, scrollPaneObstacle;
	
	private MyCanvas simCanvas;
	private MyMenu simMenu;
	
	private BorderPane simPane;

	private Scene simScene;
	private StackPane simStackPane;
	private Stage simStage;
	private VBox statusBox;
	
	private String currentBackground;
	private String[] backgrounds;
	
	
	public SimView() throws FileNotFoundException {
				
		// Initialise Stage 		
		simStage = new Stage();
		simStage.setTitle("DRONE SIMULATOR 29020945");
		simStage.setResizable(false);				
		
		// Initialise Border Pane
		simPane = new BorderPane();
		simPane.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);		
	
		Image background = new Image(new FileInputStream("graphics/dayCycle.gif"));
		ImageView image = new ImageView(background);

		
		
		// Creating Canvas
		canvasRoot = new Group();		
		simStackPane = new StackPane();
		simStackPane.setStyle("-fx-background-color: #7fb5d4");	
		simStackPane.getChildren().add(image);
		image.fitWidthProperty().bind(simStackPane.widthProperty());
		image.fitHeightProperty().bind(simStackPane.heightProperty());
			
		
		// Creating background
		backgrounds = new String[12];
		backgrounds[0] = "graphics/frame0.gif";
		backgrounds[1] = "graphics/frame1.gif";
		backgrounds[2] = "graphics/frame2.gif";
		backgrounds[3] = "graphics/frame3.gif";
		backgrounds[4] = "graphics/frame4.gif";
		backgrounds[5] = "graphics/frame5.gif";
		backgrounds[6] = "graphics/frame6.gif";
		backgrounds[7] = "graphics/frame7.gif";
		backgrounds[8] = "graphics/frame8.gif";
		backgrounds[9] = "graphics/frame9.gif";
		backgrounds[10] = "graphics/frame10.gif";
		backgrounds[11] = "graphics/frame11.gif";
		
		
		
		
		currentBackground = backgrounds[0];
		
		canvas = new Canvas(ARENA_WIDTH, ARENA_HEIGHT);		
		simStackPane.getChildren().add(canvas);
		canvasRoot.getChildren().add(simStackPane);
		
		Timer backTimer = new Timer() {
			public void run() {
				
				
				//currentBackground 
				
			}
		};
		
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
	
	/**
	 * 
	 * @return SimStage
	 */
	public Stage getSimStage() {
		
		return simStage;
		
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
	
}
	
