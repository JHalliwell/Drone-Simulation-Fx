package application;

import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Drone implements Serializable {
	
	protected int x, y, id, dx, dy, allowedDistance;
	protected static int count = 0;
	protected Direction direction;
	protected String colour;
	
	public static final int HEIGHT = 20;
	public static final int WIDTH = 20;
	
	/**
	 * Construct drone at position x,y
	 * @param x		drones x position
	 * @param y		drones y position
	 * @param d		direction of drone
	 */
public Drone(int x, int y, Direction d) {
		
		this.x = x;
		this.y = y;
		this.allowedDistance = 2;
		colour = "black";		

		direction = d.random();
		id = count++;
		this.setDirection();
	}	

	/**
	 * Is the drone at this x,y position?
	 * @param x		x position
	 * @param y		y position
	 * @return		true if drone is at x,y. False otherwise
	 */
	public boolean isHere(int x, int y, int distance) {
		
		if (this.x > (x - WIDTH - distance) && 
				this.x < (x + WIDTH + distance) &&
				this.y > (y - HEIGHT - distance) && 
				this.y < (y + HEIGHT + distance)) return true;			
		
		return false;
		
	}	
	
	/**
	 * Is the drone at this x,y position?
	 * @param x		x position
	 * @param y		y position
	 * @return		true if drone is at x,y. False otherwise
	 */
	public boolean isHere(int x, int y) {
		
		if (this.x > (x - WIDTH - 2) && 
				this.x < (x + WIDTH + 2) &&
				this.y > (y - HEIGHT - 2) && 
				this.y < (y + HEIGHT + 2)) return true;			
		
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
		
		while (!arena.canMoveHere(this.id, newx, newy)) {			
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
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getWidth() {
		return WIDTH;
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
	
}
