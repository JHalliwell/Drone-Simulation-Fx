package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.scene.image.Image;

/**
 * Stay's still unless a drone is within a certain distance,
 * then tries to move away from it
 * @author 29020945
 */
public class CautiousDrone extends Drone {

	private static final long serialVersionUID = -7073044221812250667L;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param d
	 * @param myCanvas
	 * @throws FileNotFoundException
	 */
	CautiousDrone(int x, int y, Direction d, MyCanvas myCanvas) throws FileNotFoundException {
		
		super(x, y, d, myCanvas);
		
		type = "Honer";
		allowedDistance = 100;
		dy = 3;
		dx = 3;			
		printWidth = width;
		printHeight = height;
		
		droneImage = new Image(new FileInputStream("graphics/cautious.png"));
		
	}
	
	@Override
	 public void tryToMove(DroneArena arena) {
		
		int newx = xPos;
		int newy = yPos;
		
		Drone nearbyDrone;
		
		// Initialise nearby drone if there is one nearby
		if ((nearbyDrone = isDroneNear(id, xPos, yPos, allowedDistance, width, height, 
				arena)) != null) {	
			
			// Move this drone depending on nearby drone's position
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
		
		if (newX <= 0 || newX >= arena.getWidth() - width || newY <= 0 || 
				newY >= arena.getHeight() - height) {			
			return false;
		}
		
		if (getDroneAt(newX, newY, 2, arena) != null) {		
			return false;
		}
		
		if (arena.getEnironmentAt(newX, newY, width, height) != null) {
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * Determines if a drone is near this drone, if there is, returns that nearby drone 
	 * 	(if not another cautious drone)
	 * @param id - this drone's id
	 * @param xPos - this drone's xPos
	 * @param yPos - this drone's yPos
	 * @param distance - this drone's allowedDistance to another drone
	 * @param width - this drone's width
	 * @param height - this drone's height
	 * @param arena - the main droneArena
	 * @return
	 */
	public Drone isDroneNear(int id, int xPos, int yPos, int distance, int width, 
								int height, DroneArena arena) {
			
		Drone nearbyDrone;
		
		if ((nearbyDrone = getDroneAt(xPos, yPos, allowedDistance, arena)) != null) {	
				return nearbyDrone;
		}
		
		return null;
		
	}		
	

	protected void setDirection() {
		
	}

	protected boolean canMoveHere() {
		return false;
	}
	
}
