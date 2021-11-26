package application;

import javafx.scene.shape.Rectangle;

public class Wall{
	
	String colour;
	private int height, width;
	private int xPos, yPos;
	
	public Wall(int width, int height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	public Wall(int width, int height, int xPos, int yPos) {
		
		this.width = width;
		this.height = height;		
		this.xPos = xPos;
		this.yPos = yPos;
		
		colour = "black";
		
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
	
	/**
	 * 
	 * @param droneX	drone xPos
	 * @param droneY	drone yPos
	 * @param droneWidth
	 * @param droneHeight
	 * @return True if drone overlaps with obstacle
	 */
	public boolean isHere(int droneX, int droneY, int droneWidth, int droneHeight) {
		
		if (droneX > (xPos - droneWidth - 2) && 
				droneX < (xPos + width + 2) &&
				droneY > (yPos - droneHeight - 2) && 
				droneY < (yPos + height + 2)) return true;			
		
		return false;
		
	}
	
	public void rotateLeft() {
		int temp;
		temp = width;
		width = height;
		height = temp;
	}
	
	public void rotateRight() {
		int temp;
		temp = width;
		width = height;
		height = temp;
	}
	
	public void scaleDown() {
		width *= 0.75;
		height *= 0.75;
		System.out.println("" + width + ", " + height);
	}
	
	public void scaleUp() {
		width *= 1.25;
		height *= 1.25;
		System.out.println("" + width + ", " + height);
	}
	
	public void setXPos(int x) {
		this.xPos = x;
	}
	
	public void setYPos(int y) {
		this.yPos = y;
	}
	
	public String toString() {
		
		String info = "";
		
		info += "Wall: (" + xPos + ", " + yPos + ")";
		
		return info;
		
	}
	
}
