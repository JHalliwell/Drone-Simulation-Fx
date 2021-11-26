package application;

import java.io.Serializable;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MyCanvas {

	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private int height;	
	private int width;
	
	public MyCanvas(GraphicsContext graphicsContext, Canvas canvas, int width, int height) {
		
		this.width = width;
		this.height = height;
		this.graphicsContext = graphicsContext;
		this.canvas = canvas;
		
	}
	
/**
 * Clears canvas
 */
public void clear() {
	
	graphicsContext.clearRect(0,  0,  width,  height);
	
}
	
	public void drawImage (Image i, double x, double y, double sz) {
			
			// to draw centred at x,y, give top left position and x,y size
			// sizes/position in range 0.. canvassize 
			graphicsContext.drawImage(i, x, y, sz, sz);
			
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
	
	public int getMyHeight() {
		
		return height;
		
	}
	
	public int getMyWidth() {
		
		return width;
		
	}
	
}
