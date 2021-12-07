package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Parent class of all drones 
 * @author jhalli
 */
public abstract class Drone implements Serializable {
	
	protected static int droneCount = 0;		
	
	protected transient Image droneImage, droneN, droneNE, droneE, droneSE, 
								droneS, droneSW, droneW, droneNW;	
	
	protected MyCanvas myCanvas;
	
	protected Direction direction;
	protected int height;
	protected int width;
	protected int printWidth;
	protected int printHeight;
	protected int xPos, yPos;
	protected int speed;
	protected int dx, dy;
	protected int id;	 
	protected int allowedDistance;	// Collision distance
	protected String type;
	
	Drone (int xPos, int yPos, Direction direction, MyCanvas myCanvas) 
			throws FileNotFoundException {
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.direction = direction;
		this.myCanvas = myCanvas;
		width = 40;
		height = 40;
		
		allowedDistance = 2;
		id = droneCount++;		
		setDirection();
		
	}
	
	protected abstract void tryToMove(DroneArena arena);
	
	protected abstract boolean canMoveHere(int newX, int newY, DroneArena arena);
	
	public boolean isHereWallPlacement(int otherX, int otherY, int otherWidth, 
										int otherHeight) {
		
		if (otherX > (xPos - (otherWidth / 2) - 2) && 
				otherX < (xPos + width + (otherWidth / 2) + 2) &&
				otherY > (yPos - (otherHeight / 2) - 2) && 
				otherY < (yPos + height + (otherHeight / 2) + 2)) return true;			
		
		return false;
		
	}
	
	/**
	 * 
	 * @param otherX
	 * @param otherY
	 * @param otherWidth
	 * @param otherHeight
	 * @return True if both drones overlap
	 */
	public boolean isHere(int otherX, int otherY, int otherWidth, int otherHeight) {
		
		if (otherX > (xPos - otherWidth - 2) && 
				otherX < (xPos + width + 2) &&
				otherY > (yPos - otherHeight - 2) && 
				otherY < (yPos + height + 2)) return true;			
		
		return false;
		
	}
		
	/**
	 * Check if another drone is colliding with this drone
	 * @param otherX - other drone's xPos to check against
	 * @param otherY - other drone's yPos to check against
	 * @return true if this drone is colliding with other drone
	 */
	public boolean isHere(int otherX, int otherY, int distance) {
		
		if (otherX > (xPos - width - distance) && 
				otherX < (xPos + width + distance) &&
				otherY > (yPos - height - distance) && 
				otherY < (yPos + height + distance)) {
			System.out.println("is here true");
			return true;			
		}		
		
		return false;
		
	}
	
	/**
	 * Loop through all drones, returning a drone if it's at newX, newY
	 * @param newX - xPos the drone is trying to move to
	 * @param newY - yPos the drone is trying to move to 
	 * @param arena - main droneArena
	 * @return	Drone if that drone is colliding with newX, newY
	 */
	protected Drone getDroneAt(int newX, int newY, int distance, DroneArena arena) {
		
		for (Drone d : arena.getDrones()) {
			if (d.getId() == id) continue;
			if (d.isHere(newX, newY, distance)) return d;
		}
		
		return null;
		
	}
	
	/**
	 * Change dx and dy to correspond to Direction enum
	 * @throws FileNotFoundException 
	 */
	protected abstract void setDirection();	
	
	public String toString() {		

		String info = "";				
		info += type + " Ship at (" + xPos + ", " + yPos + ")";		
		return info;
		
	}
	
	/**
	 * Sets Drone's xPos
	 * @param xPos to be set
	 */
	public void setXPos(int xPos) { this.xPos = xPos; }
	
	/**
	 * Sets Drone's yPos
	 * @param yPos to be set
	 */
	public void setYPos(int yPos) { this.yPos = yPos; }
	
	/**
	 * Sets Drone's id
	 * @param id to be set
	 */
	public void setId(int id) { this.id = id; }
	
	/**
	 * Sets Drone count
	 * @param count to be set
	 */
	public void setDroneCount(int droneCount) { Drone.droneCount = droneCount; }	
	
	/**
	 * @return Drone's height
	 */
	public int getHeight() { return height; }	
	
	/**
	 * @return Drone's width
	 */
	public int getWidth() { return width; }
	
	public Direction getDirection() { return direction; }
	
	public int getPrintWidth() { return printWidth; } 
	
	public int getPrintHeight() { return printHeight; }
	
	/**
	 * @return Drone's xPos
	 */
	public int getXPos() { return xPos; }
	
	/**
	 * @return Drone's yPos
	 */
	public int getYPos() { return yPos; }
	
	/**
	 * @return Drone's id
	 */
	public int getId() { return id; }
	
	/**
	 * @return Drone's type
	 */
	public String getType() { return type; }
	
	/**
	 * @return Drone's image
	 */
	public Image getImage() { return droneImage; }	
	
}
