package application;

import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Drone implements Serializable {
	
	private int x, y, id, dx, dy;
	private static int count = 0;
	private Direction direction;
	
	public static final int HEIGHT = 20;
	public static final int WIDTH = 20;
	
	private static int droneMoveCount = 0;
	
	/**
	 * Construct drone at position x,y
	 * @param x		drones x position
	 * @param y		drones y position
	 * @param d		direction of drone
	 */
	public Drone(int x, int y, Direction d) {
		
		this.x = x;
		this.y = y;

		direction = d;
		id = count++;
		dx = 0;		// set default direction of movement: North
		dy = 3;
		
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
			
			// If the drone cant move anywhere, we move all drones again 
			if (count > 8) { // 8 is amount of possible directions	
				// If the drone still cant move anywhere, loop from higher id
				if (droneMoveCount > 5) {
					arena.moveAllDronesFromPoint(this.id);
				}
				droneMoveCount++;
				arena.moveAllDrones();
				count = 0;
			}
						
			this.direction = direction.random();	// Move to next direction			
			this.setDirection();	// Set dx,dy from direction
			newx = x + dx;
			newy = y + dy;			
			count++;
		};
		
		x = newx;
		y = newy;
	}	
	
	/**
	 * Change dx and dy to correspond to Direction enum
	 */
	public void setDirection() {		
		if (this.direction == direction.NORTH) {
			dx = 0;
			dy = -3;
		}
		if (this.direction == direction.NORTH_EAST) {
			dx = 3;
			dy = -3;
		}
		if (this.direction == direction.EAST) {
			dx = 3;
			dy = 0;
		}
		if (this.direction == direction.SOUTH_EAST) {
			dx = 3;
			dy = 3;
		}
		if (this.direction == direction.SOUTH) {
			dx = 0;
			dy = 3;
		}
		if (this.direction == direction.SOUTH_WEST) {
			dx = -3;
			dy = 3;
		}
		if (this.direction == direction.WEST) {
			dx = -3;
			dy = 0;
		}
		if (this.direction == direction.NORTH_WEST) {
			dx = -3;
			dy = -3;
		}
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
