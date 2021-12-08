package application;

import java.io.Serializable;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MyCanvas implements Serializable {

	private transient GraphicsContext graphicsContext;
	private int height;	
	private int width;
	
	public MyCanvas (GraphicsContext graphicsContext, Canvas canvas, 
						int width, int height) {
		
		this.width = width;
		this.height = height;
		this.graphicsContext = graphicsContext;	
		
	}
	
	/**
	 * Clears canvas
	 */
	public void clear() {
		
		graphicsContext.clearRect(0,  0,  width,  height);
		
	}
	
	public void clearArea(int size, int xPos, int yPos) {
		
		graphicsContext.clearRect(xPos, yPos, size, size);
		System.out.println("clearRect");
	}
	
	public void drawImage (Image i, double xPos, double yPos, double width, double height) {
		
			graphicsContext.drawImage(i, xPos, yPos, width, height);
			
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
			graphicsContext.setLineWidth(3);
			graphicsContext.strokeRect(x, y, width, height);
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
			graphicsContext.setFill(Color.DIMGREY);
			graphicsContext.fillRect(x, y, width, height);
		}
		
		if (colour == "darkGrey") {
			graphicsContext.setFill(Color.rgb(75, 88, 115));
			graphicsContext.fillRect(x, y, width, height);
			graphicsContext.setFill(Color.SLATEGREY);
			graphicsContext.fillRect(x + 4, y + 4, width - 8, height - 8);
			
		}
		
		if (colour == "hole") {
			graphicsContext.setStroke(Color.rgb(134, 81, 80));
			graphicsContext.setLineWidth(3);
			graphicsContext.strokeOval(x, y, width, height);
		}
		
		if (colour == "hole_solid") {
			graphicsContext.setFill(Color.rgb(134, 81, 80));
			graphicsContext.fillOval(x, y, width, height);
		}
		
	}
	
	public void drawText(String s, int x, int y) {
		
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillText(s, x, y);
		
	}
		
	public int getMyHeight() {
		
		return height;
		
	}
	
	public int getMyWidth() {
		
		return width;
		
	}
	
}
