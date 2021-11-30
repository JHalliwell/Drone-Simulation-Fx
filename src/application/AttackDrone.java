package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.scene.image.Image;

public class AttackDrone extends Drone {

	int target;
	Drone targetDrone;
	boolean hasTarget;
	
	public AttackDrone(int x, int y, Direction d, MyCanvas myCanvas, DroneArena arena) 
			throws FileNotFoundException {
		
		super(x, y, d, myCanvas);
		
		direction = d.random();
		this.setDirection();
		colour = "red";
		
		// Select random target drone if there is valid (not attack) drone in arena
		if (arena.getDrones().size() > 1) {
			
			hasTarget = true;
			Random ranGen = new Random();
			target = ranGen.nextInt(id); // Between 0 and this.id
			
			while (arena.getDrones().get(target) instanceof AttackDrone) {				
				target = ranGen.nextInt(id);				
			}
		} else hasTarget = false;	
		
		System.out.println("id: " + this.id);
		System.out.println("target: " + target);
		
		this.droneImage = new Image(new FileInputStream("graphics/redDrone.png"));
						
	}
	
	public void tryToMove(DroneArena arena) {
		
		if (hasTarget) {
			// Set target drone 
			targetDrone = arena.getDrone(target);	
			
			// Change direction to follow target drone
			if (x == targetDrone.getXPos() && y > targetDrone.getYPos()) 
				direction = Direction.NORTH;
			else if (x < targetDrone.getXPos() && y > targetDrone.getYPos()) 
				direction = Direction.NORTH_EAST;
			else if (x < targetDrone.getXPos() && y == targetDrone.getYPos()) 
				direction = Direction.EAST;
			else if (x < targetDrone.getXPos() && y < targetDrone.getYPos()) 
				direction = Direction.SOUTH_EAST;
			else if (x == targetDrone.getXPos() && y < targetDrone.getYPos()) 
				direction = Direction.SOUTH;
			else if (x > targetDrone.getXPos() && y < targetDrone.getYPos()) 
				direction = Direction.SOUTH_WEST;
			else if (x > targetDrone.getXPos() && y == targetDrone.getYPos()) 
				direction = Direction.WEST;
			else if (x > targetDrone.getXPos() && y > targetDrone.getYPos()) 
				direction = Direction.NORTH_WEST;
			
			// Change speed based on direction
			setDirection();
			
			// Set new x and y 
			int newx = x += dx;
			int newy = y += dy;
			
			while (!arena.killerCanMoveHere(id, target, newx, newy, allowedDistance, width, height)) {
				
				if (count > 8) break;	// If the drone can't move anywhere, stop trying to move	
				
				direction = direction.next();	// Move to next direction			
				setDirection();	// Set dx,dy from direction
				newx = x + dx;
				newy = y + dy;			
				count++;
							
			}
			
			if (count <= 8) {
				x = newx;
				y = newy;			
			}			
		}
		

	
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
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
