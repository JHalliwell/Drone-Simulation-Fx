package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.scene.image.Image;

public class AttackDrone extends Drone {

	int target;
	Drone targetDrone;
	boolean hasTarget;
	int stuckCount = 0;
	
	public AttackDrone(int x, int y, Direction d, MyCanvas myCanvas, DroneArena arena) 
			throws FileNotFoundException {
		
		super(x, y, d, myCanvas);
		
		direction = d.random();
		this.setDirection();
		colour = "red";
		
		//System.out.println("manyDrones.size : " + arena.getDrones().size());
		
		// Select random target drone if there is valid (not attack) drone in arena
		if (arena.getDrones().size() > 0) {
			
			setTarget(arena);
			
			System.out.println("Drone " + id + " targeting Drone" + target +
								" (" + arena.getDrones().get(target).getType() + ")");
			
		} 			
		
		this.droneImage = new Image(new FileInputStream("graphics/redDrone.png"));
						
	}
	
	public void setTarget(DroneArena arena) {
		
		Random ranGen = new Random();
		target = ranGen.nextInt(id); // Between 0 and this.id
		
		System.out.println("target before while: " + target);
		System.out.println("isTarget? " + arena.getDrones().get(target).isTarget);
		
		while (arena.getDrones().get(target) instanceof AttackDrone ||
				arena.getDrones().get(target).isTarget || 
				arena.getDrones().get(target) instanceof CautiousDrone) {				
			target = ranGen.nextInt(id);	
			System.out.println("target in while: " + target);
		}
		
		arena.getDrones().get(target).setIsTarget(true);
		
	}
	
	public String getType() {
		return " attackDrone";
	}
	
	public void tryToMove(DroneArena arena) {
		
		System.out.println("Try to move Drone " + id);
		System.out.println("Stuck count : " + stuckCount);
		
		if (stuckCount < 9) {
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
		}		
		
		// Set new x and y 
		int newx = x += dx;
		int newy = y += dy;
		int breakCount = 0;
		
		while (!arena.killerCanMoveHere(id, target, newx, newy, allowedDistance, width, height)) {
			
			System.out.println("cant move here");
			System.out.println("Break count: " + breakCount);
			
			// If the drone can't move anywhere, stop trying to move
			if (breakCount > 8) {
				System.out.println("break");
				stuckCount++;
				break;	
			}				
			
			direction = direction.next();	// Move to next direction			
			setDirection();	// Set dx,dy from direction
			newx = x + dx;
			newy = y + dy;			
			breakCount++;
						
		}
		
		if (breakCount <= 8) {
			stuckCount = 0;
			x = newx;
			y = newy;	
			
		}			
	}	
	
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getTarget() {
		return target;
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
