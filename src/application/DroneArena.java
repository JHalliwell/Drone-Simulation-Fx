package application;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DroneArena implements Serializable {
	
	private MyCanvas myCanvas;
	
	private ArrayList<Environment> environment;
	private ArrayList<Drone> manyDrones;
	
	/**
	 *  Creates arrayList of drones and a canvas on which to draw arena
	 */
	DroneArena(MyCanvas myCanvas) {
		
		this.myCanvas = myCanvas;
		
		manyDrones = new ArrayList<Drone>();	
		environment = new ArrayList<Environment>();		
		
	}

	/**
	 * Adds a drone to the arena in random area moving random direction,
	 * adds to arrayList of drones, ensuring no other drone at location
	 * @param	type of drone to add 
	 * @throws FileNotFoundException 
	 */
	public void addDrone(int type) throws FileNotFoundException {     // keep
		
		int x;
		int y;
		Direction d = Direction.EAST; // Initialise, but will be made random later
		
		switch (type) {
		
		case 0 : 	// Add RandomMover at random location
			RoamDrone drone = new RoamDrone(0, 0, d.random(), myCanvas);
			do {
				Random ranGen = new Random();
				x = ranGen.nextInt(SimView.ARENA_WIDTH - drone.getWidth());
				y = ranGen.nextInt(SimView.ARENA_HEIGHT - drone.getHeight());
			} while (getDroneAt(x, y, drone.getWidth(), drone.getHeight()) != null || 
					getObstacleAt(x, y, drone.getWidth(), drone.getHeight()) != null);
			drone.setXPos(x);
			drone.setYPos(y);
			manyDrones.add(drone);
			break;
			
		case 1 :	// Add AttackDrone at random location
			AttackDrone aDrone = new AttackDrone(0, 0, d.random(), myCanvas, this);
			do {
				Random ranGen = new Random();
				x = ranGen.nextInt(SimView.ARENA_WIDTH - aDrone.getWidth());
				y = ranGen.nextInt(SimView.ARENA_HEIGHT - aDrone.getHeight());
			} while (getDroneAt(x, y, aDrone.getWidth(), aDrone.getHeight()) != null || 
					getObstacleAt(x, y, aDrone.getWidth(), aDrone.getHeight()) != null);
			aDrone.setXPos(x);
			aDrone.setYPos(y);
			manyDrones.add(aDrone);
			break;
			
		case 2 :	// Add CautiousDrone at random location
			CautiousDrone cDrone = new CautiousDrone(0, 0, d.random(), myCanvas);
			do {
				Random ranGen = new Random();
				x = ranGen.nextInt(SimView.ARENA_WIDTH - cDrone.getWidth());
				y = ranGen.nextInt(SimView.ARENA_HEIGHT - cDrone.getHeight());
			} while (getDroneAt(x, y, cDrone.getWidth(), cDrone.getHeight()) != null || 
					getObstacleAt(x, y,cDrone.getWidth(), cDrone.getHeight()) != null);
			cDrone.setXPos(x);
			cDrone.setYPos(y);
			manyDrones.add(cDrone);
			break;
		
		}
		
	}
	
	/**
	 * Loop through all drones, moving them each once
	 * @throws FileNotFoundException 
	 */
	public void moveAllDrones() throws FileNotFoundException {	// keep
		
		for (Drone d : manyDrones) {			
			d.tryToMove(this);
		}		
		
	}
	
	/**
	 * Draws arena and drones to canvas as graphics context
	 */
	public void drawArena(MyCanvas canvas) {	  // keep
		
		canvas.clear();
        
        for (Drone d : manyDrones) {
        	
    		//canvas.drawObject(d.getXPos(), d.getYPos(), d.getWidth(), d.getHeight(), "red");
    		canvas.drawImage(d.getImage(), d.getXPos(), d.getYPos(), d.getHeight());
        	
        }
       
        for (Environment e : environment) {
       	    canvas.drawObject(e.getXPos(), e.getYPos(), e.getWidth(), e.getHeight(), e.getColour());
        }      
            
	}
	
	public void drawWallPlacement(MyCanvas canvas, int x, int y, String colour, 
									Wall placementWall) {	//keep
		
		canvas.clear();
		
		drawArena(canvas);
				
		canvas.drawObject(x - (placementWall.getWidth() / 2), y - (placementWall.getHeight() / 2), 
				placementWall.getWidth(), placementWall.getHeight(), colour);
		
	}
	
	public void addEnvironment(MyCanvas canvas, int xPos, int yPos, Wall placementWall) {	// keep

		environment.add(new Wall(xPos - (placementWall.getWidth() / 2), 
				yPos - (placementWall.getHeight() / 2), placementWall.getWidth(),
				placementWall.getHeight()));

		drawArena(canvas);							
		
	}
	
	public String drawStatus() {	// keep
		
		String info = "";
		
		for (Drone d : manyDrones) 
			info += d.toString();
		
		for  (Environment e : environment) {
			info += e.toString();
		}
		
		return info;
	}
	
	public ArrayList<String> describeAll() {
		
		ArrayList<String> info = new ArrayList<String>();
		for (Drone d : manyDrones) info.add(d.toString());
		return info;
		
	}
	
	/**
	 * Search arrayList of drones to see if there's one at x,y
	 * @param x		drone x pos
	 * @param y		drone y pos
	 * @return null if no Drone there, or Drone if there is
	 */
	public Drone getDroneAtWallPlacement(int x, int y, int width, int height) {
		
		for (Drone d : manyDrones) {
			if (d.isHereWallPlacement(x, y, width, height)) return d;
		}
		
		return null;
	}
	
	/**
	 * Search arrayList of drones to see if there's one at x,y
	 * @param x		drone x pos
	 * @param y		drone y pos
	 * @return null if no Drone there, or Drone if there is
	 */
	public Drone getDroneAt(int x, int y, int width, int height) {
		
		for (Drone d : manyDrones) {
			if (d.isHere(x, y, width, height)) return d;
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
	public Drone getDroneAt(int id, int xPos, int yPos, int width, int height) {
		
		for (Drone d : manyDrones) {
			if (d.getId() == id) continue;
			if (d.isHere(xPos, yPos, width, height)) return d;			
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
	public Drone getDroneAt(int id, int xPos, int yPos, int distance, int width, 
										int height) {
		
		for (Drone d : manyDrones) {
			if (d.getId() == id) continue;
			if (d.isHere(xPos, yPos, distance, width, height)) return d;			
		}
		
		return null;		

	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public Environment getObstacleAt(int xPos, int yPos, int width, int height) {
		
		for (Environment e : environment) {
			if (e.isHere(xPos, yPos, width, height)) return e;
		}
		
		return null;
		
	}
	
	/**	 * 
	 * @return		this arena
	 */
	public DroneArena getArena() {		
		return this;		
	}
	
	/**
	 * Getter for list of drones
	 * @return		list of drones
	 */
	public ArrayList<Drone> getDrones() {		
		return this.manyDrones;		
	}
	
	/**
	 * @param id
	 * @return	drone of specified id
	 */
	public Drone getDrone(int id) {
		return manyDrones.get(id);
	}
	
	public void setDrones(ArrayList<Drone> manyDrones) {
		
		this.manyDrones = manyDrones;
		
	}	

}
