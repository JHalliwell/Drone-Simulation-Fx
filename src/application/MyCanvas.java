package application;

import java.io.Serializable;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyCanvas {

	private int width;
	private int height;
	
	private GraphicsContext graphicsContext;
	
	public MyCanvas(GraphicsContext graphicsContext, int width, int height) {
		
		this.width = width;
		this.height = height;
		this.graphicsContext = graphicsContext;
		
	}
	
	/**
	 * Draws drone to canvas
	 * @param x		drone x 
	 * @param y		drone y 
	 * @param width		drone width
	 * @param height	drone height
	 */
	public void drawObject(int x, int y, int width, int height, String colour) {
		
		if (colour == "black") {
			graphicsContext.setFill(Color.BLACK);
			graphicsContext.fillRect(x, y, width, height);
		}
		
		if (colour == "red") {
			graphicsContext.setFill(Color.RED);
			graphicsContext.fillRect(x, y, width, height);
		}
		
		if (colour == "orange") {
			graphicsContext.setFill(Color.ORANGE);
			graphicsContext.fillRect(x, y, width, height);	
		}
		
		if (colour == "grey_tran") {
			graphicsContext.setFill(Color.MEDIUMPURPLE);
			graphicsContext.fillRect(x, y, width, height);
		}
		
	}
	
	public void drawText(String s, int x, int y) {
		
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillText(s, x, y);
		
	}
	
	/**
	 * Clears canvas
	 */
	public void clear() {
		
		graphicsContext.clearRect(0,  0,  width,  height);
		
	}
	
	public int getMyHeight() {
		
		return height;
		
	}
	
	public int getMyWidth() {
		
		return width;
		
	}
	
}
