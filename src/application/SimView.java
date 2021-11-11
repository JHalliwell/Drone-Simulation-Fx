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
	private Canvas simCanvas;
	private GraphicsContext simGc;
	private BorderPane simPane;
	private HBox simHBox;
	private MenuBar simMenu;
	
	private DroneArena droneArena;

	private ArenaGrid arenaGrid;
	
	private AnimationTimer animationTimer;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 750;
	
	public SimView() {
				
		simStage = new Stage();
		simStage.setTitle("DRONE SIMULATOR");
		simStage.setResizable(false);		
				
		simPane = new BorderPane();
		simPane.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);	
		
		createButtons();
		createArena();
		createCursorCoords();
		createMenuBar();
		
		simScene = new Scene(simPane);
		simStage.setScene(simScene);
		
	}	
	
	private void createMenuBar() {
		
		simMenu = new MenuBar();
		
		Menu menuFile = new Menu("File");
		
		MenuItem menuSave = new MenuItem("Save");
		menuSave.setOnAction(e -> {
			
		});
		
		MenuItem menuLoad =  new MenuItem("Load");
		menuLoad.setOnAction(e -> {
			
		});
		
		MenuItem menuExit = new MenuItem("Exit");
		menuExit.setOnAction(e -> {
			System.exit(0);
		});
		
		menuFile.getItems().addAll(menuSave, menuLoad, menuExit);		
		simMenu.getMenus().add(menuFile);
		simPane.setTop(simMenu);
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
		
		droneArena = new DroneArena();
		//simPane.getChildren().add(droneArena.getArenaCanvas());
		simPane.setCenter(droneArena.getArenaCanvas());
		droneArena.drawArena();		
		
	}
	
	/**
	 * Creates buttons and adds to pane
	 */
	public void createButtons() {
		
		simHBox = new HBox();
		
		Button addButton = new Button();
		addButton.setText("Add Drone");		
		
		addButton.setOnAction(e -> {			

			droneArena.addDrone();
			droneArena.drawArena();	
						
		});
		
		Button playButton = new Button();
		playButton.setText("Move Drones");
		playButton.setOnAction(e -> {
			createAnimationTimer();
		});
		
		Button stopButton = new Button();
		stopButton.setText("Stop");	
		stopButton.setOnAction(e -> {
			animationTimer.stop();
		});	
		
		simHBox.getChildren().addAll(addButton, stopButton, playButton);
		simPane.setBottom(simHBox);
		simHBox.setAlignment(Pos.CENTER);
		simHBox.setSpacing(10);
		
	}
	
	private void createAnimationTimer() {
//		System.out.println("createAnimationTimer");
		
		if (animationTimer != null) {
			animationTimer.stop();
		}
		
		
		animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
//            	System.out.println("createAnimaionTimer().handle()");
            	droneArena.moveAllDrones();
            	droneArena.drawArena();
            }		
        };
        animationTimer.start();
	}
	
	/**
	 * 
	 * @return SimStage
	 */
	public Stage getSimStage() {
		
		return simStage;
		
	}
}