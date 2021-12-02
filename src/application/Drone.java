package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Parent class of all drones 
 * @author jhalli
 */
public abstract class Drone {
	
	protected static int droneCount = 0;		
	
	protected Image droneImage;
	protected Image droneN;
	protected Image droneNE;
	protected Image droneE;
	protected Image droneSE;
	protected Image droneS;
	protected Image droneSW;
	protected Image droneW;
	protected Image droneNW;	
	
	protected MyCanvas myCanvas;
	
	protected Direction direction;
	protected int height;
	protected int width;
	protected int xPos, yPos;
	protected int speed;
	protected int dx, dy;
	protected int id;	 
	protected int allowedDistance;	// Collision distance
	protected String type;
	
	Drone (int xPos, int yPos, Direction direction, MyCanvas myCanvas) {
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.direction = direction;
		this.myCanvas = myCanvas;
		
		allowedDistance = 2;
		id = droneCount++;
		setDirection();
		
	}
	
	protected abstract void tryToMove(DroneArena arena);
	
	protected abstract boolean canMoveHere(int newX, int newY, DroneArena arena);	
	
	public boolean isHereWallPlacement(int otherX, int otherY, int otherWidth, int otherHeight) {
		
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
	 * Is the drone at this x,y position?
	 * @param x		x position
	 * @param y		y position
	 * @return		true if drone is at x,y. False otherwise
	 */
	public boolean isHere(int otherX, int otherY, int distance, int otherWidth, int otherHeight) {
		
		System.out.println("this.x,y : " + xPos + ", " + yPos);
		System.out.println("other.x,y : " + otherX + ", " + otherY);
		
		//System.out.println("is here");
		
		if (otherX > (xPos - otherWidth - distance) && 
				otherX < (xPos + otherWidth + distance) &&
				otherY > (yPos - otherHeight - distance) && 
				otherY < (yPos + otherHeight + distance)) {
			System.out.println("is here true");
			return true;			
		}
		
		//System.out.println("is here false");
		
		return false;
		
	}
	
	/**
	 * Change dx and dy to correspond to Direction enum
	 * @throws FileNotFoundException 
	 */
	protected void setDirection() {	
		
		if (direction == Direction.NORTH) {
			//droneImage = droneN;
			dx = 0;
			dy = -speed;
		}
		if (direction == Direction.NORTH_EAST) {
			//droneImage = droneNE;
			dx = speed;
			dy = -speed;
		}
		if (direction == Direction.EAST) {
			//droneImage = droneE;
			dx = speed;
			dy = 0;
		}
		if (direction == Direction.SOUTH_EAST) {
			//droneImage = droneSE;
			dx = speed;
			dy = speed;
		}
		if (direction == Direction.SOUTH) {
			//droneImage = droneS;
			dx = 0;
			dy = speed;
		}
		if (direction == Direction.SOUTH_WEST) {
			//droneImage = droneSW;
			dx = -speed;
			dy = speed;
		}
		if (direction == Direction.WEST) {
			//droneImage = droneW;
			dx = -speed;
			dy = 0;
		}
		if (direction == Direction.NORTH_WEST) {
			//droneImage = droneNW;
			dx = -speed;
			dy = -speed;
		}
		
	}	
	
	public String toString() {
		
		String info = "";		
		info += id + " -- Position: (" + xPos + ", " + yPos + ")" + "\n";		
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
