package application;

import java.io.Serializable;

import javafx.scene.image.Image;

/**
 * Parent Class of all environment objects
 * @author 29020945
 */
public abstract class Environment implements Serializable {

	private static final long serialVersionUID = 7465794064538223031L;
	
	protected String colour;
	protected transient Image image;
	protected int width, height;
	protected int xPos, yPos;
	protected String type;
	protected int distance;
	
	/**
	 * @param xPos
	 * @param yPos
	 */
	Environment(int xPos, int yPos){
		
		this.xPos = xPos;
		this.yPos = yPos;		
		
	}
	
	/**
	 * @param xPos
	 * @param yPos
	 * @param width
	 * @param height
	 */
	Environment(int xPos, int yPos, int width, int height) {
	
		this.xPos = xPos;
		this.yPos = yPos;		
		this.width = width;
		this.height = height;
		
	}
	
	/**
	 * Checks if this object is at drone location
	 * @param droneX
	 * @param droneY
	 * @param droneWidth
	 * @param droneHeight
	 * @return true if object is at drone's location, else false
	 */
	public abstract boolean isHere(int droneX, int droneY, int droneWidth, int droneHeight);
	
	/**
	 * Checks if this object is at other object's location
	 * @param otherX
	 * @param otherY
	 * @param otherWidth
	 * @param otherHeight
	 * @return true if object is at other object's location location, else false
	 */
	public abstract boolean isHereEnvironmentPlacement(int otherX, int otherY, int otherWidth,
												int otherHeight);

	public String toString() {
		
		String info = "";
		
		info += type + " at (" + xPos + ", " + yPos + ")";
		
		return info;
		
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public String getColour() {
		return colour;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
}
