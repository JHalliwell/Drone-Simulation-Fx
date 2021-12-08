package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class AttackDrone extends Drone implements Serializable {

	int target;
	Drone targetDrone;
	boolean hasTarget;
	int stuckCount = 0;
	SoundEffects soundEffects;
	
	public AttackDrone(int xPos, int yPos, Direction direction, MyCanvas myCanvas, DroneArena arena) 
			throws FileNotFoundException {
		
		super(xPos, yPos, direction, myCanvas);		
		
		
		type = "Attack";
		width = 40;
		height = 40;
		printWidth = width;
		printHeight = height;
		speed = 2;
		
		soundEffects = new SoundEffects();
		droneImage = new Image(new FileInputStream("graphics/greyShip.png"));
		
		setDirection();
		
		// Select random target drone if there is valid (not attack) drone in arena
		if (arena.getDrones().size() > 0) setTarget(arena);			
		
		
						
	}
	
	public void setTarget(DroneArena arena) {
		
		Random ranGen = new Random();
		target = ranGen.nextInt(id); // Between 0 and this.id		
			
		while (!(arena.getDrones().get(target) instanceof RoamDrone) ||
				((RoamDrone) arena.getDrones().get(target)).getIsTarget()) {				
			target = ranGen.nextInt(id);
		}
		
		((RoamDrone) arena.getDrones().get(target)).setIsTarget(true);		
		
	}
	
	public String getType() {
		return " attackDrone";
	}
	
	@Override
	public void tryToMove(DroneArena arena) {		
			
		// Set target drone 
		targetDrone = arena.getDrone(target);	
		
		// Change direction to follow target drone
		if (xPos == targetDrone.getXPos() && yPos > targetDrone.getYPos()) 
			direction = Direction.NORTH;
		else if (xPos < targetDrone.getXPos() && yPos > targetDrone.getYPos()) 
			direction = Direction.NORTH_EAST;
		else if (xPos < targetDrone.getXPos() && yPos == targetDrone.getYPos()) 
			direction = Direction.EAST;
		else if (xPos < targetDrone.getXPos() && yPos < targetDrone.getYPos()) 
			direction = Direction.SOUTH_EAST;
		else if (xPos == targetDrone.getXPos() && yPos < targetDrone.getYPos()) 
			direction = Direction.SOUTH;
		else if (xPos > targetDrone.getXPos() && yPos < targetDrone.getYPos()) 
			direction = Direction.SOUTH_WEST;
		else if (xPos > targetDrone.getXPos() && yPos == targetDrone.getYPos()) 
			direction = Direction.WEST;
		else if (xPos > targetDrone.getXPos() && yPos > targetDrone.getYPos()) 
			direction = Direction.NORTH_WEST;
		
		// Change speed based on direction
		setDirection();
				
		
		// Set new x and y 
		int newx = xPos += dx;
		int newy = yPos += dy;
		int breakCount = 0;
		
		while (!canMoveHere(newx, newy, arena)) {
			
			// If the drone can't move anywhere, stop trying to move
			if (breakCount > 8) break;			
			
			direction = direction.next();	// Move to next direction			
			setDirection();	// Set dx,dy from direction
			newx = xPos + dx;
			newy = yPos + dy;			
			breakCount++;
						
		}
		
		if (breakCount <= 8) {
			xPos = newx;
			yPos = newy;	
			
		}			
	}	
	
	/**
	 * Checks if the drone is within the border, and if other attack drone is at x,y
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean canMoveHere(int newX, int newY, DroneArena arena) {
		
		if (newX <= 0 || newX >= arena.getWidth() - width || newY <= 0 || 
				newY >= arena.getHeight() - width) {
			return false;
		}
		
		// Copy drone list from arena
		ArrayList<Drone> manyDrones = arena.getDrones();
		
		for (Drone d : manyDrones) {
			
			if (d.getId() == id) continue;			
			if (d.getId() != target && d.isHere(newX, newY, allowedDistance)) {
				return false;
			}
			if (d.getId() == target && d.isHere(newX, newY, 0)) {
				
				soundEffects.playExplosion();
				
				manyDrones.remove(id);
				manyDrones.remove(d);
				
				arena.resetDroneList();
				
			}			
			
		}
		
		if (arena.getObstacleAt(newX, newY, width, height) != null) return false;
		
		return true;
		
	}
	
	/**
	 * Change dx and dy to correspond to Direction enum
	 * @throws FileNotFoundException 
	 */
	protected void setDirection() {	
		
		if (direction == Direction.NORTH) {
			dx = 0;
			dy = -speed;
		}
		if (direction == Direction.NORTH_EAST) {
			dx = speed;
			dy = -speed;
		}
		if (direction == Direction.EAST) {
			dx = speed;
			dy = 0;
		}
		if (direction == Direction.SOUTH_EAST) {
			dx = speed;
			dy = speed;
		}
		if (direction == Direction.SOUTH) {
			dx = 0;
			dy = speed;
		}
		if (direction == Direction.SOUTH_WEST) {
			dx = -speed;
			dy = speed;
		}
		if (direction == Direction.WEST) {
			dx = -speed;
			dy = 0;
		}
		if (direction == Direction.NORTH_WEST) {
			dx = -speed;
			dy = -speed;
		}
		
	}	
		
	public int getTarget() {
		return target;
	}	
	
}
