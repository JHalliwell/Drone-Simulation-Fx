package application;
	
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {
	
	private int shapeOneX = 10;
	private int shapeOneY = 10;
	private int shapeTwoX = 210;
	private int shapeTwoY = 210;
	
	private static int SHAPE_WIDTH = 40;
	private static int SHAPE_HEIGHT = 40;
	
	private Canvas canvas;
	private Pane pane;
	private Scene scene;
	
	private boolean wKeyPressed;
	private boolean aKeyPressed;
	private boolean sKeyPressed;
	private boolean dKeyPressed;
	
	private AnimationTimer animationTimer;
	
    @Override
    public void start(Stage stage) throws IOException {        
       
        stage.setResizable(false);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        
        pane = new Pane();
        canvas = new Canvas(500,500);        
        
        pane.getChildren().add(canvas);
        
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        
        animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
            	moveShapes();
                drawShapes();
            }		
        };
        animationTimer.start();        
       
        keyHandlers();        
        
    }
    
    private void moveShapes() {
    	if (wKeyPressed) shapeOneY--;
    	if (aKeyPressed) shapeOneX--;
    	if (sKeyPressed) shapeOneY++;
    	if (dKeyPressed) shapeOneX++;
    }
    
    private void drawShapes() {
    	
    	GraphicsContext area = canvas.getGraphicsContext2D();
    	area.setFill(Color.ALICEBLUE);
    	area.fillRect(0, 0, 500, 500);
    	
    	if (shapeOneX > (shapeTwoX - SHAPE_WIDTH) && 
    			shapeOneX < (shapeTwoX + SHAPE_WIDTH) &&
    			shapeOneY > (shapeTwoY - SHAPE_HEIGHT) &&
    			shapeOneY < (shapeTwoY + SHAPE_HEIGHT)) {
    		
    		GraphicsContext shapeOne = canvas.getGraphicsContext2D();
        	shapeOne.setFill(Color.RED);
        	shapeOne.fillRect(shapeOneX, shapeOneY, SHAPE_WIDTH, SHAPE_HEIGHT);
        	
    	} else {
    		
    		GraphicsContext shapeOne = canvas.getGraphicsContext2D();
        	shapeOne.setFill(Color.BLUE);
        	shapeOne.fillRect(shapeOneX, shapeOneY, SHAPE_WIDTH, SHAPE_HEIGHT);
        	
    	}   	
    	
    	GraphicsContext shapeTwo = canvas.getGraphicsContext2D();
    	shapeTwo.setFill(Color.BLUE);
    	shapeTwo.fillRect(shapeTwoX, shapeTwoY, SHAPE_WIDTH, SHAPE_HEIGHT);
    	
    }
    
    private void keyHandlers() {
    	
    	scene.setOnKeyPressed(e -> {
    		switch(e.getCode()) {
    		case W : wKeyPressed = true; break; 
    		case A : aKeyPressed = true; break; 
    		case S : sKeyPressed = true; break; 
    		case D : dKeyPressed = true; break; 
    		}
    	});
    	
    	scene.setOnKeyReleased(e -> {
    		switch(e.getCode()) {
    		case W : wKeyPressed = false; break;
    		case A : aKeyPressed = false; break;
    		case S : sKeyPressed = false; break;
    		case D : dKeyPressed = false; break;
    		}
    	});
    	
    }

    public static void main(String[] args) {
        launch();
    }
}