package application;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Drone {
	private int x, y, id, dx, dy;
	private static int count = 0;
	private Direction direction;
	private Polygon drone;
	private Point2D movement;
	
	public static final int HEIGHT = 6;
	public static final int WIDTH = 6;
	
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
		dy = 1;
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
	
	/**
	 * Is the drone at this x,y position?
	 * @param x		x position
	 * @param y		y position
	 * @return		true if drone is at x,y. False otherwise
	 */
	public boolean isHere(int x, int y) {
		if (this.x == x && this.y == y) {			
			return true;
		}			
		return false;
	}
	
	/**
	 * display the drone on the canvas
	 * @param c		the canvas in which the drones are shown
	 */
	public void displayDrones(ArenaGrid arenaGrid) {			
		arenaGrid.showIt(x, y, "D");
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
		
		while (!arena.canMoveHere(newx, newy)) {
			
			// If the drone cant move anywhere, we move all drones again i
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
			dy = -1;
		}
		if (this.direction == direction.NORTH_EAST) {
			dx = 1;
			dy = -1;
		}
		if (this.direction == direction.EAST) {
			dx = 1;
			dy = 0;
		}
		if (this.direction == direction.SOUTH_EAST) {
			dx = 1;
			dy = 1;
		}
		if (this.direction == direction.SOUTH) {
			dx = 0;
			dy = 1;
		}
		if (this.direction == direction.SOUTH_WEST) {
			dx = -1;
			dy = 1;
		}
		if (this.direction == direction.WEST) {
			dx = -1;
			dy = 0;
		}
		if (this.direction == direction.NORTH_WEST) {
			dx = -1;
			dy = -1;
		}
	}
}
