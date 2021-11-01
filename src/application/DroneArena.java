package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DroneArena {
	
	private ArrayList<Drone> manyDrones;
	
	DroneArena() {
		manyDrones = new ArrayList<Drone>();			
	}
	
	public void doDisplay(Canvas canvas) {		
		
		// Constructor will create 2D array representing arena 
        ArenaGrid arenaGrid = new ArenaGrid(SimView.ARENA_WIDTH, SimView.ARENA_HEIGHT);
        
        // Puts 'D' in arena for each drone
        this.showDrones(arenaGrid);
        
        // Draw the drones to the canvas
        arenaGrid.drawDrones(canvas);
	}
	
	/**
	 * show all the drones in the interface, cycle through arrayList of drones
	 * @param c		the canvas in which the drones are shown
	 */
	public void showDrones(ArenaGrid arenaGrid) {
		for (Drone d : manyDrones) d.displayDrone(arenaGrid);
	}
	
	/**
	 * Loop through all drones, moving them each once
	 */
	public void moveAllDrones() {
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
	public boolean canMoveHere(int x, int y) {		
		if (x < 0 || x >= SimView.ARENA_WIDTH || y < 0 || y >= SimView.ARENA_HEIGHT) return false;
		if (getDroneAt(x, y) != null) {			
			return false;
		}
		return true;
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
			x = ranGen.nextInt(SimView.ARENA_WIDTH);
			y = ranGen.nextInt(SimView.ARENA_HEIGHT);
		} while (getDroneAt(x, y) != null); // Check for another drone at location (null if no drone)
		
		System.out.println("addDrone() x=" + x + " y=" + y);

		manyDrones.add(new Drone(x, y, d.random()));
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

}
