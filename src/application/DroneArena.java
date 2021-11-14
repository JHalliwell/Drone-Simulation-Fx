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
	
	/**
	 *  Creates arrayList of drones and a canvas on which to draw arena
	 */
	DroneArena() {
		
		manyDrones = new ArrayList<Drone>();	
		
	}

	/**
	 * Draws arena and drones to canvas as graphics context
	 */
	public void drawArena(MyCanvas canvas) {	  
		
		canvas.clear();
		
        for (int i = 0; i < manyDrones.size(); i++) {        	
        	canvas.drawDrone(manyDrones.get(i).getXPos(), manyDrones.get(i).getYPos(), 
        			Drone.WIDTH, Drone.HEIGHT);      
        }
       
	}
	
	/**
	 * Adds a drone to the arena in random area moving random direction,
	 * adds to arrayList of drones, ensuring no other drone at location
	 */
	public void addDrone() {
		
		int x;
		int y;
		Direction d = Direction.EAST; // Initialise, but will be made random later
		do {
			Random ranGen = new Random();
			x = ranGen.nextInt(SimView.ARENA_WIDTH - Drone.WIDTH);
			y = ranGen.nextInt(SimView.ARENA_HEIGHT - Drone.HEIGHT);
		} while (getDroneAt(x, y) != null); // Check for another drone at location (null if no drone)
		
//		System.out.println("addDrone() x=" + x + " y=" + y);

		manyDrones.add(new Drone(x, y, d.random()));
		
	}
	
	/**
	 * Loop through all drones, moving them each once
	 */
	public void moveAllDrones() {
		
//		System.out.println("moveAllDrones()");
		for (Drone d : manyDrones) d.tryToMove(this);
		
	}
	
	/**
	 * Loop through drones from a certain id, moving each once
	 * @param i
	 */
	public void moveAllDronesFromPoint(int i) {
		for (i += 1; i < manyDrones.size(); i++) {
			manyDrones.get(i).tryToMove(this);
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
			System.out.println("border conditions");
			return false;
		}
		
		if (getDroneAtId(id, x, y) != null) {		
			System.out.println("getDroneAt != null");
			return false;
		}
		return true;
	}
	
	/**
	 * Search arrayList of drones, excluding this.id, to see if there's one at x,y
	 * @param id	id of a drone to ignore
	 * @param x		drone x pos
	 * @param y		drone y pos
	 * @return null if no Drone there, or Drone if there is
	 */
	public Drone getDroneAtId(int id, int x, int y) {
		
		for (int i = 0; i < manyDrones.size(); i++) {
			
			if (manyDrones.get(i).getId() == id) {
				continue;
			}
			
			if (manyDrones.get(i).isHere(x, y)) {
				return manyDrones.get(i);
			}
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
			if (d.isHere(x, y)) {
				return d;
			}
		}
		return null;
	}
	
	public void printArenaInfo() {
		
		for (int i = 0; i < manyDrones.size(); i++) {
			System.out.println(manyDrones.get(i).getId());
		}
		
	}
	
	/**
	 * Setter for arenaCanvas
	 * @param canvas	canvas to set 
	 */
//	public void setCanvas(Canvas canvas) {
//		
//		this.arenaCanvas = canvas;
//		
//	}
	
	
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
	
	/**	 
	 * @return this arena
	 */
	public DroneArena getArena() {
		
		return this;
		
	}
	

}
