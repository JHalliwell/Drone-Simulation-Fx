package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class AttackDrone extends Drone {

	int target;
	Drone targetDrone;
	boolean hasTarget;
	int stuckCount = 0;
	
	public AttackDrone(int xPos, int yPos, Direction direction, MyCanvas myCanvas, DroneArena arena) 
			throws FileNotFoundException {
		
		super(xPos, yPos, direction, myCanvas);		
		
		setDirection();
		type = "Attack";
		width = 50;
		height = 50;
		speed = 3;
		
		// Select random target drone if there is valid (not attack) drone in arena
		if (arena.getDrones().size() > 0) setTarget(arena);			
		
		droneImage = new Image(new FileInputStream("graphics/redDrone.png"));
						
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
		
		if (newX <= 0 || newX >= SimView.ARENA_WIDTH - width || newY <= 0 || 
				newY >= SimView.ARENA_HEIGHT - width) {
			return false;
		}
		
		// Copy drone list from arena
		ArrayList<Drone> manyDrones = arena.getDrones();
		
		//System.out.println("Target: " + target);
		
		for (Drone d : manyDrones) {
			
			//System.out.println("drone id: " + d.getId());
			
			if (d.getId() == id) continue;			
			if (d.getId() != target && d.isHere(newX, newY, allowedDistance, width, height)) {
				return false;
			}
			if (d.getId() == target && d.isHere(newX, newY, 0, width, height)) {
				
				System.out.println("killed");
				
				manyDrones.remove(id);
				manyDrones.remove(d);
				
				// Set drone id's to match index after removals
				for (int i = 0; i < manyDrones.size(); i++) {
					manyDrones.get(i).setId(i);
				}
				
				// Give attack drones new targets after removals
				for (Drone dr : manyDrones) {
					if (dr instanceof RoamDrone) {
						((RoamDrone) dr).setIsTarget(false);
					}					
				}
				
				for (Drone dr : manyDrones) {
					if (dr instanceof AttackDrone) {
						((AttackDrone) dr).setTarget(arena);
					}
				}
				
				arena.setDrones(manyDrones); // Set arena list to edited list
				Drone.droneCount = manyDrones.size();
				arena.drawStatus();
				
			}			
			
		}
		
		if (arena.getObstacleAt(newX, newY, width, height) != null) return false;
		
		return true;
		
	}
		
	public int getTarget() {
		return target;
	}	
	
}
