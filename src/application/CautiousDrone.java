package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class CautiousDrone extends Drone {

	CautiousDrone(int x, int y, Direction d, MyCanvas myCanvas) throws FileNotFoundException {
		
		super(x, y, d, myCanvas);
		
		type = "Cautious";
		allowedDistance = 50;
		dy = 3;
		dx = 3;		
		width = 50;
		height = 50;
						
		droneImage = new Image(new FileInputStream("graphics/yellowDrone.png"));
		
	}
	
	@Override
	 public void tryToMove(DroneArena arena) {
		
		int newx = xPos;
		int newy = yPos;
		
		Drone nearbyDrone;
		
		if ((nearbyDrone = isDroneNear(id, xPos, yPos, allowedDistance, width, height, arena)) != null) {			
			if (nearbyDrone.getXPos() < xPos) {
				if (canMoveHere(newx + dx, newy, arena)) newx += dx;
			}
			
			if (nearbyDrone.getXPos() > xPos) {
				if (canMoveHere(newx - dx, newy, arena)) newx -= dx;
			}
			
			if (nearbyDrone.getYPos() < yPos) {
				if (canMoveHere(newx, newy + dy, arena)) newy += dy;
			}
			
			if (nearbyDrone.getYPos() > yPos) {
				if (canMoveHere(newx, newy - dy, arena)) newy -= dy;
			}				
		}		
		
		xPos = newx;
		yPos = newy;
	
	}
	
	/**
	 * Determines whether a drone can be moved to x,y pos, checked if
	 * x,y is in arena and if there's a drone there already
	 * @param x		x co-ord
	 * @param y		y co-ord
	 * @return		false: drone move here, true: drone can move here
	 */
	public boolean canMoveHere(int newX, int newY, DroneArena arena) {	
		
		if (newX <= 0 || newX >= SimView.ARENA_WIDTH - width || newY <= 0 || 
				newY >= SimView.ARENA_HEIGHT - height) {			
			return false;
		}
		
		if (arena.getDroneAt(id, newX, newY, width, height) != null) {		
			return false;
		}
		
		if (arena.getObstacleAt(newX, newY, width, height) != null) {
			return false;
		}
		
		return true;
		
	}
	
	public Drone isDroneNear(int id, int xPos, int yPos, int distance, int width, int height, DroneArena arena) {
		
		Drone nearbyDrone;
		
		if ((nearbyDrone = arena.getDroneAt(id, xPos, yPos, distance, width, height)) != null) {	
			System.out.println("Drone is near");			
			return nearbyDrone;
		}
		
		return null;
		
	}


	protected boolean canMoveHere() {
		return false;
	}
	
}
