package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Polygon;

public class Drone implements Serializable {
	
	protected int x, y, id, dx, dy, allowedDistance;
	protected static int count = 0;
	protected Direction direction;
	protected String colour;
	protected Image droneImage;
	String imageFile = "graphics/reg_drone.png";
	
	protected int height = 40;
	protected int width = 40;
	
	/**
	 * Construct drone at position x,y
	 * @param x		drones x position
	 * @param y		drones y position
	 * @param d		direction of drone
	 * @throws FileNotFoundException 
	 */
public Drone(int x, int y, Direction d) throws FileNotFoundException {
		
		this.x = x;
		this.y = y;
		this.allowedDistance = 2;
		colour = "black";		

		direction = d.random();
		id = count++;
		this.setDirection();
		
		droneImage = new Image(new FileInputStream(imageFile));
		
	}	

	/**
	 * Is the drone at this x,y position?
	 * @param x		x position
	 * @param y		y position
	 * @return		true if drone is at x,y. False otherwise
	 */
	public boolean isHere(int otherX, int otherY, int distance, int otherWidth, int otherHeight) {
		
		if (otherX > (this.x - otherWidth - distance) && 
				otherX < (this.x + otherWidth + distance) &&
				otherY > (this.y - otherHeight - distance) && 
				otherY < (this.y + otherHeight + distance)) return true;			
		
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
		
		if (otherX > (this.x - otherWidth - 2) && 
				otherX < (this.x + otherWidth + 2) &&
				otherY > (this.y - otherHeight - 2) && 
				otherY < (this.y + otherHeight + 2)) return true;			
		
		return false;
		
	}	

	/**
	 * Try to move drone, check with canMoveHere, if cant, change direction
	 * if can, change x and y
	 * @param arena
	 */
	public void tryToMove(DroneArena arena) {

		int newx = x + dx;
		int newy = y + dy;
		int count = 0; // To see if all directions have been tried		
		
		while (!arena.canMoveHere(this.id, newx, newy, width, height)) {			
			if (count > 8) break;	// If the drone can't move anywhere, stop trying to move	
						
			this.direction = direction.random();	// Move to next direction			
			this.setDirection();	// Set dx,dy from direction
			newx = x + dx;
			newy = y + dy;			
			count++;
		};
		
		// Only move if drone can move to 'empty' location
		if (count <= 8) {			
			x = newx;
			y = newy;
		}
		
	}	
	
	/**
	 * Change dx and dy to correspond to Direction enum
	 */
	public void setDirection() {	
		
		if (this.direction == direction.NORTH) {
			dx = 0;
			dy = -2;
		}
		if (this.direction == direction.NORTH_EAST) {
			dx = 2;
			dy = -2;
		}
		if (this.direction == direction.EAST) {
			dx = 2;
			dy = 0;
		}
		if (this.direction == direction.SOUTH_EAST) {
			dx = 2;
			dy = 2;
		}
		if (this.direction == direction.SOUTH) {
			dx = 0;
			dy = 2;
		}
		if (this.direction == direction.SOUTH_WEST) {
			dx = -2;
			dy = 2;
		}
		if (this.direction == direction.WEST) {
			dx = -2;
			dy = 0;
		}
		if (this.direction == direction.NORTH_WEST) {
			dx = -2;
			dy = -2;
		}
		
	}
	
	public Image getImage() {
		return droneImage;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public String getColour() {
		return colour;
	}
	
	public int getXSpeed() {
		return this.dx;
	}
	
	public int getYSpeed() {
		return this.dy;
	}
	
	public int getXPos() {
		return this.x;
	}
	
	public int getYPos() {
		return this.y;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setXPos(int x) {
		this.x = x;
	}
	
	public void setYPos(int y) {
		this.y = y;		
	}
	
	public String toString() {
		
		String info = "";
		
		info += id + " -- Position: (" + x + ", " + y + ")" + "\n";
		
		return info;
		
	}
	
}
