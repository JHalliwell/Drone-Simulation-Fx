package application;

public class CautiousDrone extends Drone {

	CautiousDrone(int x, int y, Direction d) {
		
		super(x, y, d);
		
		allowedDistance = 50;
		colour = "orange";
		dy = 3;
		dx = 3;
		
	}
	
	public void tryToMove(DroneArena arena) {
		
		int newx = x;
		int newy = y;
		
		Drone nearbyDrone;
		
		if ((nearbyDrone = arena.isDroneNear(id, x, y, allowedDistance, width, height)) != null) {			
			if (nearbyDrone.getXPos() < x) {
				if (arena.canMoveHere(id, newx + dx, newy, width, height)) newx += dx;
			}
			
			if (nearbyDrone.getXPos() > x) {
				if (arena.canMoveHere(id, newx - dx, newy, width, height)) newx -= dx;
			}
			
			if (nearbyDrone.getYPos() < y) {
				if (arena.canMoveHere(id, newx, newy + dy, width, height)) newy += dy;
			}
			
			if (nearbyDrone.getYPos() > y) {
				if (arena.canMoveHere(id, newx, newy - dy, width, height)) newy -= dy;
			}				
		}		
		
		x = newx;
		y = newy;

	}
	
//	public void tryToMove(DroneArena arena) {
//		
//		int newx = x;
//		int newy = y;
//		
//		System.out.println("newx :" + newx + " newy: " + newy);
//		
//		Drone nearbyDrone;
//		
//		if ((nearbyDrone = arena.isDroneNear(id, x, y, allowedDistance)) != null) {
//			
//			if (nearbyDrone.getXPos() < x) {
//				System.out.println("1x < 2x");
//				
//				x += dx;
//			}
//			
//			if (nearbyDrone.getXPos() > x) {
//				System.out.println("1x < 2x");
//				
//				x -= dx;
//			}
//			
//			if (nearbyDrone.getYPos() < y) {
//				System.out.println("1x < 2x");
//				
//				y += dy;
//			}
//			
//			if (nearbyDrone.getYPos() > y) {
//				System.out.println("1x < 2x");
//				
//				y -= dy;
//			}
//			
//		}		
//
//	}
	
	
	

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
