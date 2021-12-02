package application;

import javafx.scene.image.Image;

public abstract class Environment {

	String colour;
	Image image;
	protected int width, height;
	protected int xPos, yPos;
	
	Environment(int xPos, int yPos){
		
		this.xPos = xPos;
		this.yPos = yPos;		
		
	}
	
	public abstract boolean isHere(int droneX, int droneY, int droneWidth, int droneHeight);
	
	public String toString() {
		
		String info = "";
		
		info += "Wall: (" + xPos + ", " + yPos + ")";
		
		return info;
		
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
