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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test extends Application {
	
	private static int SHAPE_HEIGHT = 60;
	private static int SHAPE_WIDTH = 60;
	public static void main(String[] args) {
        launch();
    }
	private boolean aKeyPressed;

	private AnimationTimer animationTimer;
	private Canvas canvas;
	private boolean dKeyPressed;
	Image droneImage;
	
	private Pane pane;
	
	private Scene scene;
	private int shapeOneDx = 2;
	
	private int shapeOneDy = 2;
	private int shapeOneX = 10;
	private int shapeOneY = 10;
	
	private int shapeTwoDx = 2;
	private int shapeTwoDy = 2;
	private int shapeTwoX = 210;
	private int shapeTwoY = 210;
	
	private boolean sKeyPressed;
	
    private boolean wKeyPressed;
    
    private boolean canGoHere(int x, int y) {
    	if (x <= 0 || x > 800 || y <= 0 || 
				y >= 800 - SHAPE_HEIGHT) {
    		return false;
    	}
    	
    	return true;
    }
    
    private void drawInfo() {
    	
    	String s = "ShapeOne: " + shapeOneX + "," + shapeOneY + "\n";
    	s += "ShapeTwo: " + shapeTwoX + ", " + shapeTwoY + "\n";
    	s += "ShapeOne dx: " + shapeOneDx + ", shapeOne dy: " + shapeOneDy + "\n";
    	s += "ShapeTwo dx: " + shapeTwoDx + ", shapeTwo dy: " + shapeTwoDy + "\n"; 
    	
    	GraphicsContext text = canvas.getGraphicsContext2D();
    	text.setFill(Color.BLACK);
    	text.fillText(s, 2, 10);
    	    	
    }
    
    private void drawShapes() throws FileNotFoundException {
    	
    	GraphicsContext area = canvas.getGraphicsContext2D();
    	area.setFill(Color.BEIGE);
    	area.fillRect(0, 0, 800, 800);
    	
    	if (shapesCollided()) {
    		
    		GraphicsContext shapeOne = canvas.getGraphicsContext2D();
        	shapeOne.setFill(Color.RED);
        	shapeOne.fillRect(shapeOneX, shapeOneY, SHAPE_WIDTH, SHAPE_HEIGHT);
        	
    	} else {
    		
    		GraphicsContext shapeOne = canvas.getGraphicsContext2D();
    		shapeOne.drawImage(droneImage, shapeOneX, shapeOneY, SHAPE_WIDTH, SHAPE_HEIGHT);
        	
    	}   	
    	
    	GraphicsContext shapeTwo = canvas.getGraphicsContext2D();
    	shapeTwo.drawImage(droneImage, shapeTwoX, shapeTwoY, SHAPE_WIDTH, SHAPE_HEIGHT);
    	
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
    
    private void moveShapes() {
    	
    	if (wKeyPressed) shapeOneY -= shapeOneDy;
    	if (aKeyPressed) shapeOneX -= shapeOneDx;
    	if (sKeyPressed) shapeOneY += shapeOneDy;
    	if (dKeyPressed) shapeOneX += shapeOneDx;
    	
    	int newx = shapeTwoX;
    	int newy = shapeTwoY;
    	
    	
    	if (shapeIsNear()) {
    		
    		System.out.println("0  " + newx + ", " + newy);
    		
    		if (shapeOneX < shapeTwoX) {
    			System.out.println("1x < 2x");
    			if(canGoHere(newx - shapeTwoDx, newy)) { 
    				newx += shapeTwoDx;
        			System.out.println("1  " + newx + ", " + newy);
    			}
    				
    		}
    		
    		if (shapeOneX > shapeTwoX) {
    			System.out.println("1x > 2x");
    			if(canGoHere(newx - shapeTwoDx, newy)) {
    				newx -= shapeTwoDx;
        			System.out.println("2  " + newx + ", " + newy);
    			}
    				
    		}
    		
    		if (shapeOneY < shapeTwoY) {
    			System.out.println("1y < 2y");
    			if(canGoHere(newx, newy + shapeTwoDy)) {
    				newy += shapeTwoDy;
        			System.out.println("3  " + newx + ", " + newy);
    			}
    				
    		}
    		
    		if (shapeOneY > shapeTwoY) {
    			System.out.println("1y > 2y");
    			if(canGoHere(newx, newy - shapeTwoDy)) {
    				newy -= shapeTwoDy;
        			System.out.println("4  " + newx + ", " + newy);
    			}
    				
    		}
    		
    		shapeTwoX = newx;
    		shapeTwoY = newy;
    	}
    	
    }
    
    private boolean shapeIsNear() {
    	
    	if (shapeOneX > (shapeTwoX - SHAPE_WIDTH - 30) && 
    			shapeOneX < (shapeTwoX + SHAPE_WIDTH + 30) &&
    			shapeOneY > (shapeTwoY - SHAPE_HEIGHT - 30) &&
    			shapeOneY < (shapeTwoY + SHAPE_HEIGHT + 30)) {
    		return true;
    	}
    	
    	return false;
    	
    }
    
    private boolean shapesCollided() {
    	
    	if (shapeOneX > (shapeTwoX - SHAPE_WIDTH) && 
    			shapeOneX < (shapeTwoX + SHAPE_WIDTH) &&
    			shapeOneY > (shapeTwoY - SHAPE_HEIGHT) &&
    			shapeOneY < (shapeTwoY + SHAPE_HEIGHT)) {
    		return true;
    	}
    	
    	return false;
    }

    @Override
    public void start(Stage stage) throws IOException {  
    	
    	droneImage = new Image(new FileInputStream("graphics/reg_drone.png"));
       
        stage.setResizable(false);
        stage.setMinWidth(800);
        stage.setMinHeight(800);
        
        pane = new Pane();
        canvas = new Canvas(800,800);        
        
        pane.getChildren().add(canvas);
        pane.setMinSize(800, 800);
        
        scene = new Scene(pane);
        
        stage.setScene(scene);
        stage.show();
        
        animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
            	moveShapes();
                try {
					drawShapes();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                drawInfo();
            }		
        };
        animationTimer.start();        
       
        keyHandlers();        
        
    }
}