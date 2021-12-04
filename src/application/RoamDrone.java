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
	public RoamDrone(int xPos, int yPos, Direction direction, 
						MyCanvas myCanvas) throws FileNotFoundException  {
		
		super(xPos, yPos, direction, myCanvas);	
		
		width = 45;
		height = 45;
		printWidth = width;
		printHeight = height;
		speed = 2;
		type = "Roamer";
		
		setImages();	
		setDirection();
		
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
		
		if (newX <= 0 || newX >= arena.getWidth() - width || newY <= 0 || 
				newY >= arena.getHeight() - height) {			
			return false;
		}
		
		if (getDroneAt(newX, newY, allowedDistance, arena) != null) {		
			return false;
		}
		
		if (arena.getObstacleAt(newX, newY, width, height) != null) {
			return false;
		}
		
		return true;
		
	}
	
	@Override
	public String toString() {
		
		String info = "";				
		info += type + " Ship at (" + xPos + ", " + yPos + ")";	
		
		if (isTarget) info += " is being targeted!";
		
		return info;
		
	}
	
	protected void setImages() throws FileNotFoundException {
		
		droneN = new Image(new FileInputStream("graphics/roamShipN.png"));
		droneNE = new Image(new FileInputStream("graphics/roamShipNE.png"));
		droneE = new Image(new FileInputStream("graphics/roamShipE.png"));
		droneSE = new Image(new FileInputStream("graphics/roamShipSE.png"));
		droneS = new Image(new FileInputStream("graphics/roamShipS.png"));
		droneSW = new Image(new FileInputStream("graphics/roamShipSW.png"));
		droneW = new Image(new FileInputStream("graphics/roamShipW.png"));
		droneNW = new Image(new FileInputStream("graphics/roamShipNW.png"));
		
	}
	
	/**
	 * Change dx and dy to correspond to Direction enum
	 * @throws FileNotFoundException 
	 */
	protected void setDirection() {	
		
		if (direction == Direction.NORTH) {
			printWidth = width;
			printHeight = height;
			droneImage = droneN;
			dx = 0;
			dy = -speed;
		}
		if (direction == Direction.NORTH_EAST) {
			printWidth = (int)(width * 1.35);
			printHeight = (int)(height * 1.35);
			droneImage = droneNE;
			dx = speed;
			dy = -speed;
		}
		if (direction == Direction.EAST) {
			printWidth = width;
			printHeight = height;
			droneImage = droneE;
			dx = speed;
			dy = 0;
		}
		if (direction == Direction.SOUTH_EAST) {
			printWidth = (int)(width * 1.35);
			printHeight = (int)(height * 1.35);
			droneImage = droneSE;
			dx = speed;
			dy = speed;
		}
		if (direction == Direction.SOUTH) {
			printWidth = width;
			printHeight = height;
			droneImage = droneS;
			dx = 0;
			dy = speed;
		}
		if (direction == Direction.SOUTH_WEST) {
			printWidth = (int)(width * 1.35);
			printHeight = (int)(height * 1.35);
			droneImage = droneSW;
			dx = -speed;
			dy = speed;
		}
		if (direction == Direction.WEST) {
			printWidth = width;
			printHeight = height;
			droneImage = droneW;
			dx = -speed;
			dy = 0;
		}
		if (direction == Direction.NORTH_WEST) {
			printWidth = (int)(width * 1.35);
			printHeight = (int)(height * 1.35);
			droneImage = droneNW;
			dx = -speed;
			dy = -speed;
		}
		
	}	
		
	public void setIsTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}
	
	
	public boolean getIsTarget() {
		return isTarget;
	}
	
}
