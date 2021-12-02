package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;

public class RoamDrone extends Drone implements Serializable {

	protected boolean isTarget; // True if targeted by attack drone
	
	/**
	 * Construct drone at position x,y
	 * @param x		drones x position
	 * @param y		drones y position
	 * @param d		direction of drone
	 * @throws FileNotFoundException 
	 */
	public RoamDrone(int xPos, int yPos, Direction direction, MyCanvas myCanvas) throws FileNotFoundException  {
		
		super(xPos, yPos, direction, myCanvas);	
		
		width = 50;
		height = 50;
		speed = 4;
		
		setDirection();
		
		droneImage = new Image(new FileInputStream("graphics/regDroneN.png"));
		
	}	
	
	
	/**
	 * Try to move drone, check with canMoveHere, if cant, change direction
	 * if can, change x and y
	 * @param arena
	 */	
	public void tryToMove(DroneArena arena) {
		
		int newX = xPos + dx;
		int newY = yPos + dy;
		int count = 0; // To see if all directions have been tried		
		
		while (!canMoveHere(newX, newY, arena)) {			
			if (count > 8) break;	// If the drone can't move anywhere, stop trying to move	
						
			direction = direction.random();	// Move to next direction			
			setDirection();	// Set dx,dy from direction
			newX = xPos + dx;
			newY = yPos + dy;			
			count++;
		};
		
		// Only move if drone can move to 'empty' location
		if (count <= 8) {			
			xPos = newX;
			yPos = newY;
		}
		
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
		
	public void setIsTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}
	
	
	public boolean getIsTarget() {
		return isTarget;
	}
	
}
