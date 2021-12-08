package application;

import java.io.Serializable;

import javafx.scene.image.Image;

public abstract class Environment implements Serializable {

	String colour;
	transient Image image;
	protected int width, height;
	protected int xPos, yPos;
	protected String type;
	protected int distance;
	
	Environment(int xPos, int yPos){
		
		this.xPos = xPos;
		this.yPos = yPos;		
		
	}
	
	Environment(int xPos, int yPos, int width, int height) {
	
		this.xPos = xPos;
		this.yPos = yPos;		
		this.width = width;
		this.height = height;
		
	}
	
	public abstract boolean isHere(int droneX, int droneY, int droneWidth, int droneHeight);
	
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
