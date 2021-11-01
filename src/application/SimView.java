package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SimView extends VBox{
	
	Canvas canvas;
	
	public SimView() {
		this.canvas = new Canvas(400,400);
		
		this.getChildren().addAll(this.canvas);
	}
	
	public void draw() {
		GraphicsContext g = this.canvas.getGraphicsContext2D();
        
        g.setFill(Color.LIGHTCORAL);
        g.fillRect(0, 0, DroneArena.ARENA_WIDTH, DroneArena.ARENA_HEIGHT);
	}
}