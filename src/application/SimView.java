package application;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SimView extends VBox{	
	
	public int ARENA_HEIGHT;
	public int ARENA_WIDTH;
	private int WINDOW_HEIGHT;
	private int WINDOW_WIDTH;	
	private DroneArena arena;
	private Buttons buttons;
	private Canvas canvas;
	private Group canvasRoot;
	private ScrollPane scrollPaneDrone;
	private Popup aboutPopup;
	private Label aboutLabel;
	private Image aboutImage;
	private ImageView aboutImageView;
	
	private MyCanvas simCanvas;
	private MyMenu simMenu;
	
	private BorderPane simPane;

	private Scene simScene;
	private StackPane simStackPane;
	private Stage simStage;
	private VBox statusBox;

	private ImageView image;

	
	
	public SimView() throws FileNotFoundException {
				
		int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
	    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

	    
	    WINDOW_WIDTH = (int)(screenWidth * 0.9);
	    WINDOW_HEIGHT = (int)(screenHeight * 0.9);
        
        ARENA_WIDTH = (int)(WINDOW_WIDTH * 0.85);
        ARENA_HEIGHT = (int)(WINDOW_HEIGHT * 0.88);
		
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
		
		Image test = new Image(new FileInputStream("graphics/spaceBackground.bmp"));
		image = new ImageView(test);
		image.setFitWidth(ARENA_WIDTH);
		image.setFitHeight(ARENA_HEIGHT);
		
		simStackPane.getChildren().add(image);
		
		canvas = new Canvas(ARENA_WIDTH, ARENA_HEIGHT);
		simCanvas = new MyCanvas(canvas.getGraphicsContext2D(), canvas, ARENA_WIDTH, ARENA_HEIGHT);
		simStackPane.getChildren().add(canvas);		
		
		canvasRoot.getChildren().add(simStackPane);				
		
		// Create status area
		scrollPaneDrone = new ScrollPane();
		scrollPaneDrone.setPrefWidth(WINDOW_WIDTH * 0.15);
		scrollPaneDrone.setPrefHeight(WINDOW_HEIGHT * 0.68);
		scrollPaneDrone.setFitToWidth(true);
    	statusBox = new VBox();   	
    	
    	scrollPaneDrone.setContent(statusBox);    	
    	
		// Create drone arena
		arena = new DroneArena(simCanvas, ARENA_WIDTH, ARENA_HEIGHT);			
						
		// Initialise menu, buttons
		simMenu = new MyMenu(this, arena, simCanvas);
		buttons = new Buttons(arena, simCanvas, canvas, simPane);		
			
		// Add to border pane: menu, buttons
		simPane.setTop(simMenu);
		simPane.setBottom(buttons);	
		simPane.setLeft(canvasRoot);	
		simPane.setRight(scrollPaneDrone);
		
		simPane.setPadding(new Insets(5));
		BorderPane.setMargin(buttons, new Insets(5));
		BorderPane.setMargin(scrollPaneDrone, new Insets(5));
		BorderPane.setMargin(canvasRoot, new Insets(5));
		
		// Add popup about
		aboutPopup = new Popup();
		aboutPopup.setX(200);
		aboutPopup.setY(200);	
		aboutPopup.setAnchorX(105);
		aboutPopup.setAnchorY(100);
		aboutPopup.setHideOnEscape(true);
		aboutPopup.setAutoHide(true);
		
		aboutImage = new Image(new FileInputStream("graphics/about.png"));
		aboutImageView = new ImageView(aboutImage);
		
		aboutLabel = new Label();
		aboutLabel.setGraphic(aboutImageView);
		aboutLabel.setMinSize(200, 200);
		
		aboutPopup.getContent().add(aboutLabel);
		
		drawStatus();
		
		// Start animation timer for status
		startAnimationTimer();
		
		// Initialise scene and add pane, set scene to stage
		simScene = new Scene(simPane);
		simScene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
		simStage.setScene(simScene);

	}	
	
	public void showPopup() {
		
		aboutPopup.show(simStage);
		
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
	
	// Create About pop up window
	Popup aboutWindow = new Popup();
	
	
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
	
