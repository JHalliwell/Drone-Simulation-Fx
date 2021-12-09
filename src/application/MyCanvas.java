package application;

import java.io.Serializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Methods for drawing/clearing on a canvas
 * @author 29020945
 */
public class MyCanvas implements Serializable {

	private static final long serialVersionUID = -8324992548375973185L;
	private transient GraphicsContext graphicsContext;
	private int width; // Arena dimensions
	private int height;	
	
	
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

	/**
	 * Draws an image onto the canvas
	 * @param i - Image to draw
	 * @param xPos - of image
	 * @param yPos - of image
	 * @param width - of image
	 * @param height - of image
	 */
	public void drawImage (Image i, int xPos, int yPos, int width, int height) {		
			graphicsContext.drawImage(i, xPos, yPos, width, height);			
	}
	
	/**
	 * Draws drone to canvas
	 * @param xPos - of drone
	 * @param yPos - of drone
	 * @param width - of drone
	 * @param height - of drone
	 * @param type - Determines what shape/colour to draw
	 */
	public void drawObject(int xPos, int yPos, int width, int height, String type) {		
		if (type == "black") {
			graphicsContext.setFill(Color.BLACK);
			graphicsContext.setLineWidth(3);
			graphicsContext.strokeRect(xPos, yPos, width, height);
		}
		
		if (type == "red") {
			graphicsContext.setFill(Color.RED);
			graphicsContext.fillRect(xPos, yPos, width, height);
		}
		
		if (type == "orange") {
			graphicsContext.setFill(Color.ORANGE);
			graphicsContext.fillRect(xPos, yPos, width, height);	
		}
		
		if (type == "grey_tran") {
			graphicsContext.setFill(Color.DIMGREY);
			graphicsContext.fillRect(xPos, yPos, width, height);
		}
		
		if (type == "darkGrey") {
			graphicsContext.setFill(Color.rgb(75, 88, 115));
			graphicsContext.fillRect(xPos, yPos, width, height);
			graphicsContext.setFill(Color.SLATEGREY);
			graphicsContext.fillRect(xPos + 4, yPos + 4, width - 8, height - 8);
			
		}
		
		if (type == "hole") {
			graphicsContext.setStroke(Color.rgb(134, 81, 80));
			graphicsContext.setLineWidth(3);
			graphicsContext.strokeOval(xPos, yPos, width, height);
		}
		
		if (type == "hole_solid") {
			graphicsContext.setFill(Color.rgb(134, 81, 80));
			graphicsContext.fillOval(xPos, yPos, width, height);
		}		
	}
	
}
