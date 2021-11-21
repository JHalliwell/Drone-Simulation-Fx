package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DroneArena implements Serializable {
	
	private int arenaWidth;
	private int arenaHeight;
	
	private ArrayList<Drone> manyDrones;
	private ArrayList<Wall> environment;
	
	/**
	 *  Creates arrayList of drones and a canvas on which to draw arena
	 */
	DroneArena() {
		
		manyDrones = new ArrayList<Drone>();	
		environment = new ArrayList<Wall>();
		
		addWalls();
		
	}
	
	public void addWalls() {
		
		environment.add(new Wall(300, 50, 0, 30));
		
	}

	/**
	 * Draws arena and drones to canvas as graphics context
	 */
	public void drawArena(MyCanvas canvas) {	  
		
		canvas.clear();
        
        for (Drone d : manyDrones) {
        	canvas.drawObject(d.getXPos(), d.getYPos(), d.getWidth(), d.getHeight(), d.getColour());
        }
        
        for (Wall w : environment) {
        	canvas.drawObject(w.getXPos(), w.getYPos(), w.getWidth(), w.getHeight(), w.getColour());
        }
        
        
       
	}
	
	/**
	 * Adds a drone to the arena in random area moving random direction,
	 * adds to arrayList of drones, ensuring no other drone at location
	 * @param	type of drone to add 
	 */
	public void addDrone(int type) {
		
		int x;
		int y;
		Direction d = Direction.EAST; // Initialise, but will be made random later
		do {
			Random ranGen = new Random();
			x = ranGen.nextInt(SimView.ARENA_WIDTH - Drone.WIDTH);
			y = ranGen.nextInt(SimView.ARENA_HEIGHT - Drone.HEIGHT);
		} while (getDroneAt(x, y) != null); // Check for another drone at location (null if no drone)
		
		if (type == 0) manyDrones.add(new Drone(x, y, d.random()));
		if (type == 1) manyDrones.add(new AttackDrone(x, y, d.random()));	
		if (type == 2) manyDrones.add(new CautiousDrone(x, y, d.random()));
		
	}
	
	/**
	 * Loop through all drones, moving them each once
	 */
	public void moveAllDrones() {
		
		for (Drone d : manyDrones) {			
			d.tryToMove(this);
		}
		
	}

	/**
	 * Determines whether a drone can be moved to x,y pos, checked if
	 * x,y is in arena and if there's a drone there already
	 * @param x		x co-ord
	 * @param y		y co-ord
	 * @return		false: drone move here, true: drone can move here
	 */
	public boolean canMoveHere(int id, int x, int y) {	
		
		if (x <= 0 || x >= SimView.ARENA_WIDTH - Drone.WIDTH || y <= 0 || 
				y >= SimView.ARENA_HEIGHT - Drone.HEIGHT) {			
			return false;
		}
		
		if (getDroneAt(id, x, y) != null) {		
			return false;
		}
		
		return true;
		
	}

	/**
	 * Checks if the drone is within the border, and if other attack drone is at x,y
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean killerCanMoveHere(int id, int x, int y, int distance) {
		
		if (x <= 0 || x >= SimView.ARENA_WIDTH - Drone.WIDTH || y <= 0 || 
				y >= SimView.ARENA_HEIGHT - Drone.HEIGHT) {
			return false;
		}
		
		for (Drone d : manyDrones) {
			if (d.getId() == id) continue;
			if (d instanceof AttackDrone && d.isHere(x, y, distance)) return false;
			if (!(d instanceof AttackDrone) && d.isHere(x, y, distance)) manyDrones.remove(d);
		}		
		
		return true;
		
	}
	
	public Drone isDroneNear(int id, int x, int y, int distance) {
			
		Drone nearbyDrone;
		
		if ((nearbyDrone = getDroneAtDistance(id, x, y, distance)) != null) {	
			System.out.println("Drone is near");			
			return nearbyDrone;
		}
		
		return null;
		
	}
	
	/**
	 * Search arrayList of drones, excluding this.id, to see if there's one at x,y
	 * @param id	id of a drone to ignore
	 * @param x		drone x pos
	 * @param y		drone y pos
	 * @return null if no Drone there, or Drone if there is
	 */
	public Drone getDroneAt(int id, int x, int y) {
		
		for (Drone d : manyDrones) {
			if (d.getId() == id) continue;
			if (d.isHere(x, y)) return d;			
		}
		
		return null;		

	}
	
	/**
	 * Search arrayList of drones, excluding this.id, to see if there's one at x,y
	 * @param id	id of a drone to ignore
	 * @param x		drone x pos
	 * @param y		drone y pos
	 * @return null if no Drone there, or Drone if there is
	 */
	public Drone getDroneAtDistance(int id, int x, int y, int distance) {
		
		for (Drone d : manyDrones) {
			if (d.getId() == id) continue;
			if (d.isHere(x, y, distance)) return d;			
		}
		
		return null;		

	}
	
	/**
	 * Search arrayList of drones to see if there's one at x,y
	 * @param x		drone x pos
	 * @param y		drone y pos
	 * @return null if no Drone there, or Drone if there is
	 */
	public Drone getDroneAt(int x, int y) {
		
		for (Drone d : manyDrones) {
			if (d.isHere(x, y)) return d;
		}
		
		return null;
	}
		
	public void setDrones(ArrayList<Drone> drones) {
		
		this.manyDrones = drones;
		
	}
	
	/**
	 * Getter for list of drones
	 * @return		list of drones
	 */
	public ArrayList<Drone> getDrones() {
		
		return this.manyDrones;
		
	}	
	
	/**	 * 
	 * @return		this arena
	 */
	public DroneArena getArena() {
		
		return this;
		
	}

}
