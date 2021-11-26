package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
	
	public static final int ARENA_HEIGHT = 850;
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
	private Image[] backgrounds;
	private ImageView image;
	private int backgroundNo = 0;
	private Image frame0, frame1, frame2, frame3, frame4;
	private Image frame5, frame6, frame7, frame8, frame9, frame10, frame11;
	
	
	public SimView() throws FileNotFoundException {
				
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
		
		// Creating background
		frame0 = new Image(new FileInputStream("graphics/frame0.png"));
		frame1 = new Image(new FileInputStream("graphics/frame1.gif"));
		frame2 = new Image(new FileInputStream("graphics/frame2.gif"));
		frame3 = new Image(new FileInputStream("graphics/frame3.gif"));
		frame4 = new Image(new FileInputStream("graphics/frame4.gif"));
		frame5 = new Image(new FileInputStream("graphics/frame5.gif"));
		frame6 = new Image(new FileInputStream("graphics/frame6.gif"));
		frame7 = new Image(new FileInputStream("graphics/frame7.gif"));
		frame8 = new Image(new FileInputStream("graphics/frame8.gif"));
		frame9 = new Image(new FileInputStream("graphics/frame9.gif"));
		frame10 = new Image(new FileInputStream("graphics/frame10.gif"));
		frame11 = new Image(new FileInputStream("graphics/frame11.gif"));
		
		backgrounds = new Image[12];
		backgrounds[0] = frame0;
		backgrounds[1] = frame1;
		backgrounds[2] = frame2;
		backgrounds[3] = frame3;
		backgrounds[4] = frame4;
		backgrounds[5] = frame5;
		backgrounds[6] = frame6;
		backgrounds[7] = frame7;
		backgrounds[8] = frame8;
		backgrounds[9] = frame9;
		backgrounds[10] = frame10;
		backgrounds[11] = frame11;	
		
		Image test = new Image(new FileInputStream("graphics/spaceBackground.bmp"));
		image = new ImageView(test);
		image.setFitWidth(ARENA_WIDTH);
		image.setFitHeight(ARENA_HEIGHT);
		
		simStackPane.getChildren().add(image);
		
		canvas = new Canvas(ARENA_WIDTH, ARENA_HEIGHT);
		simCanvas = new MyCanvas(canvas.getGraphicsContext2D(), canvas, ARENA_WIDTH, ARENA_HEIGHT);
		simStackPane.getChildren().add(canvas);		
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
		        @Override
		        public void run() {
		        	if (backgroundNo == 11)
						backgroundNo = 0;
					else backgroundNo++;				

		        }
		    }, 0, 5000);				
		
		canvasRoot.getChildren().add(simStackPane);				
		
		// Create status area
		scrollPaneDrone = new ScrollPane();
		scrollPaneDrone.setPrefWidth(170);
		scrollPaneDrone.setFitToWidth(true);
    	statusBox = new VBox();
    	
    	scrollPaneDrone.setContent(statusBox);
    	
		// Create drone arena
		arena = new DroneArena(simCanvas);	
		
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
//				image.setImage(backgrounds[backgroundNo]);
//				simStackPane.getChildren().clear();
//				simStackPane.getChildren().add(image);
//				simStackPane.getChildren().add(canvas);
            }	
			
		};
		
		at.start();
	}
	
}
	
