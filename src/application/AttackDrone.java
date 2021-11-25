package application;

public class AttackDrone extends Drone {

	private static int height = 25;
	private static int width = 25;
	
	public AttackDrone(int x, int y, Direction d) {
		
		super(x, y, d);
		
		direction = d.random();
		this.setDirection();
		colour = "red";
						
	}
	
	public void tryToMove(DroneArena arena) {
		
		int newx = x + dx;
		int newy = y + dy;
		int count = 0; // To see if all directions have been tried		
		
		while (!arena.killerCanMoveHere(this.id, newx, newy, allowedDistance, width, height)) {			
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
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
