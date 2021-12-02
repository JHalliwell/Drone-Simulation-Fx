package application;

import java.io.Serializable;

public class Wall extends Environment implements Serializable {
	
	public Wall(int xPos, int yPos, int width, int height) {
		
		super(xPos, yPos);
		
		this.width = width;
		this.height = height;
		colour = "black";
		
	}
	
	public Wall(int xPos, int yPos) {
		
		super(xPos, yPos);
		
		width = 200;
		height = 20;
		
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
	
}
