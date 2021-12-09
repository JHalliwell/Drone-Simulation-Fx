package application;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Interface for simulator application
 * @author 29020945
 */
public class SimView {	
	
	// Dimensions for window and arena
	private int arenaWidth;
	private int arenaHeight;
	private int windowWidth;
	private int windowHeight;
	
	private Stage simStage; 
	private Scene simScene;
	
	private BorderPane simPane; // BorderPane for storing different simulation nodes
	
	private MyMenu simMenu;	// My menu class which inherits Menu
		
	// Objects for displaying arena
	private DroneArena arena;
	private Group canvasRoot;
	private StackPane simStackPane;
	private Image backgroundImage;
	private ImageView backgroundImageView;
	private Canvas canvas;
	private MyCanvas simCanvas;
	
	private Buttons buttons;	// Buttons class which inherits HBox
	
	// Objects for status box	
	private VBox statusBox;
	private ScrollPane scrollPane;
	
	public SimView() throws IOException {
		
		// Get screen dimensions of host machine
		int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
	    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
	    
	    // Set window and arena dimensions based on host screen size
	    windowWidth = (int)(screenWidth * 0.85);
	    windowHeight = (int)(screenHeight * 0.85);        
        arenaWidth = (int)(windowWidth * 0.8);
        arenaHeight = (int)(windowHeight * 0.92);
		
		// Initialise Stage 		
		simStage = new Stage();
		simStage.setTitle("DRONE SIMULATOR 29020945");
		simStage.setResizable(false);	
		
		// Initialise Border Pane
		simPane = new BorderPane();
		simPane.setMinSize(windowWidth, windowHeight);		
			
		// Creating arena area 
		canvasRoot = new Group();		
		simStackPane = new StackPane();			
		backgroundImage = new Image(new FileInputStream("graphics/spaceBackground.bmp"));
		backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setFitWidth(arenaWidth);
		backgroundImageView.setFitHeight(arenaHeight);		
		simStackPane.getChildren().add(backgroundImageView);		
		canvas = new Canvas(arenaWidth, arenaHeight);
		simCanvas = new MyCanvas(canvas.getGraphicsContext2D(), canvas, arenaWidth, arenaHeight);
		simStackPane.getChildren().add(canvas);			
		canvasRoot.getChildren().add(simStackPane);				
		arena = new DroneArena(simCanvas, arenaWidth, arenaHeight);		
		
		// Create status area
		scrollPane = new ScrollPane();
		scrollPane.setPrefWidth(windowWidth * 0.2);
		scrollPane.setPrefHeight(arenaHeight);
		scrollPane.setFitToWidth(true);
    	statusBox = new VBox();   	    	
    	scrollPane.setContent(statusBox);  
    	startAnimationTimer(); // Animation timer for status
						
		// Initialise menu, buttons
		simMenu = new MyMenu(this, simStage, arena, simCanvas);
		buttons = new Buttons(arena, simCanvas, canvas, simPane);	
		buttons.setPrefHeight(windowHeight * 0.02);
				
		// Add nodes to border pane
		simPane.setTop(simMenu);
		simPane.setBottom(buttons);	
		simPane.setLeft(canvasRoot);	
		simPane.setRight(scrollPane);
		
		// Format border pane
		simPane.setPadding(new Insets(5));
		BorderPane.setMargin(buttons, new Insets(5));
		BorderPane.setMargin(scrollPane, new Insets(5));
		BorderPane.setMargin(canvasRoot, new Insets(5));		
		
		// Initialise scene and add pane, set scene to stage
		simScene = new Scene(simPane);
		simScene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
		simStage.setScene(simScene);

	}	
	
	/**
	 * Gets the arena info and adds it to status box
	 */
	private void drawStatus() {
		
		statusBox.getChildren().clear();
		
		ArrayList<String> info = new ArrayList<String>();
		info = arena.describeAll();		

		for (String i : info) {
			
			Label l = new Label(i);
			statusBox.getChildren().add(l);
			
		}	
		
	}
	
	/**
	 * Animation timer for status box
	 */
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
	 * @return windowWidth
	 */
	public int getWindowWidth() {
		return windowWidth;
	}
	
	/**
	 * @return windowHeight
	 */
	public int getWindowHeight() {
		return windowHeight;
	}
	
	/**
	 * @return SimStage
	 */
	public Stage getSimStage() {		
		return simStage;		
	}
	
}
	
