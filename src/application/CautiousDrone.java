package application;

public class CautiousDrone extends Drone {

	CautiousDrone(int x, int y, Direction d) {
		
		super(x, y, d);
		
		colour = "orange";
		dy = 3;
		dx = 3;
		
	}
	
public void tryToMove(DroneArena arena) {

	    int count = 0; // To see if all directions have been tried		
		
		while (arena.isDroneNear(this.id, x, y, 10)) {			
			if (count > 8) break;	// If the drone can't move anywhere, stop trying to move	
						
			this.direction = direction.next();	// Move to next direction			
			this.setDirection();	// Set dx,dy from direction
						
			count++;
		};		
		
		
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
	
}
