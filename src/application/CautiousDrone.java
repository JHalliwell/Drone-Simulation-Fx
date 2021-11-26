package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class CautiousDrone extends Drone {

	CautiousDrone(int x, int y, Direction d, MyCanvas myCanvas) throws FileNotFoundException {
		
		super(x, y, d, myCanvas);
		
		allowedDistance = 50;
		colour = "orange";
		dy = 3;
		dx = 3;		
		
		this.droneImage = new Image(new FileInputStream("graphics/yellowDrone.png"));
		
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
	
}
