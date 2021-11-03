package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DroneArena {
	
	public static final int ARENA_WIDTH = 500;
	public static final int ARENA_HEIGHT = 400;
	public static final int ARENA_LEFT_BORDER = 10;
	public static final int ARENA_RIGHT_BORDER = 510;
	public static final int ARENA_TOP_BORDER = 10;
	public static final int ARENA_BOTTOM_BORDER = 410;
	
	private Scene arenaScene;
	private Canvas arenaCanvas;
	
	private ArrayList<Drone> manyDrones;
	
	/**
	 *  Creates arrayList of drones and a canvas on which to draw arena
	 */
	DroneArena() {
		
		manyDrones = new ArrayList<Drone>();		
		arenaCanvas = new Canvas(ARENA_WIDTH, ARENA_HEIGHT);
		
	}

	/**
	 * Draws arena and drones to canvas as graphics context
	 */
	public void drawArena() {	
		System.out.println("drawArena()");
		
		GraphicsContext gc = arenaCanvas.getGraphicsContext2D();
        gc.setFill(Color.AQUA);
        gc.fillRect(ARENA_LEFT_BORDER, ARENA_TOP_BORDER, ARENA_WIDTH, ARENA_HEIGHT);   
        
		gc.setFill(Color.BLACK);
        for (int i = 0; i < manyDrones.size(); i++) {
        	
        	int x = manyDrones.get(i).getXPos();
        	int y = manyDrones.get(i).getYPos();
        	
        	gc.fillRect(x, y, Drone.WIDTH, Drone.HEIGHT);
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
			x = ARENA_LEFT_BORDER + ranGen.nextInt(ARENA_RIGHT_BORDER - ARENA_LEFT_BORDER - (Drone.WIDTH * 2));
			y = ARENA_TOP_BORDER + ranGen.nextInt(ARENA_BOTTOM_BORDER - ARENA_TOP_BORDER - (Drone.HEIGHT * 2));
		} while (getDroneAt(x, y) != null); // Check for another drone at location (null if no drone)
		
		System.out.println("addDrone() x=" + x + " y=" + y);

		manyDrones.add(new Drone(x, y, d.random()));
		
	}
	
	/**
	 * Loop through all drones, moving them each once
	 */
	public void moveAllDrones() {
		System.out.println("moveAllDrones()");
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
		if (x <= 0 || x >= ARENA_RIGHT_BORDER - Drone.WIDTH || y <= 0 || 
				y >= ARENA_HEIGHT - Drone.HEIGHT) {
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
	 * 
	 * @return arenaCanvas
	 */
	public Canvas getArenaCanvas() {
		
		return arenaCanvas;
		
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
			
			manyDrones.get(i).isHere(x, y);
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

}
