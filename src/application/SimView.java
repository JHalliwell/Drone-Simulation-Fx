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

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 750;
	public static final int ARENA_WIDTH = 500;
	public static final int ARENA_HEIGHT = 400;
	public static final int ARENA_LEFT_BORDER = 10;
	public static final int ARENA_RIGHT_BORDER = 510;
	public static final int ARENA_TOP_BOREDER = 10;
	public static final int ARENA_BOTTOM_BORDER = 410;
	
	public SimView() {
		simStage = new Stage();
		simStage.setTitle("DRONE SIMULATOR");
		simStage.setResizable(false);
//		simStage.setMinWidth(WINDOW_WIDTH);
//		simStage.setMinHeight(WINDOW_HEIGHT);	
		
		simPane = new AnchorPane();
		simPane.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		simScene = new Scene(simPane);
		simStage.setScene(simScene);
		
		simCanvas = new Canvas(ARENA_WIDTH, ARENA_HEIGHT);			
		
		drawArenaBorder();
		createButtons();		
		
		droneArena = new DroneArena();
	}
	
	public void drawArenaBorder() {	
		simGc = simCanvas.getGraphicsContext2D();
        simGc.setFill(Color.AQUA);
        simGc.fillRect(ARENA_LEFT_BORDER, ARENA_TOP_BOREDER, ARENA_WIDTH, ARENA_HEIGHT);  
        simPane.getChildren().addAll(simCanvas);
	}
	
	public void createButtons() {
		Button addButton = new Button();
		addButton.setText("Add Drone");
		addButton.setLayoutX(600);
		addButton.setLayoutY(50);
		addButton.setPrefSize(100, 20);	
		
		addButton.setOnAction(e -> {
			droneArena.addDrone();
			droneArena.doDisplay(simCanvas);			
		});
		
		Button stopButton = new Button();
		stopButton.setText("Stop");
		stopButton.setLayoutX(600);
		stopButton.setLayoutY(100);
		stopButton.setPrefSize(100, 20);	
		
		simPane.getChildren().addAll(addButton, stopButton);		
	}
	
	public Stage getSimStage() {
		return simStage;
	}
}