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
	private Wall placementWall;
	
	/**
	 *  Creates arrayList of drones and a canvas on which to draw arena
	 */
	DroneArena() {
		
		manyDrones = new ArrayList<Drone>();	
		environment = new ArrayList<Wall>();
		
		placementWall = new Wall(300, 50);
		
	}

	/**
	 * Draws arena and drones to canvas as graphics context
	 */
	public void drawArena(MyCanvas canvas) {	  
		
		canvas.clear();
        
        for (Drone d : manyDrones) {
        	canvas.drawObject(d.getXPos(), d.getYPos(), d.getWidth(), d.getHeight(), d.getColour());
        	String s = "" + d.getId();
        	canvas.drawText(s, d.getXPos() + (d.getWidth() / 2), d.getYPos() + (d.getHeight() / 2));
        }
       
        for (Wall w : environment) {
       	    canvas.drawObject(w.getXPos(), w.getYPos(), w.getWidth(), w.getHeight(), w.getColour());
        }
        
        canvas.drawText(this.drawStatus(), 10, 10);
       
            
	}
	
	public void drawWallPlacement(MyCanvas canvas, int x, int y) {
		
		canvas.clear();
		
		drawArena(canvas);
				
		canvas.drawObject(x - (placementWall.getWidth() / 2), y - (placementWall.getHeight() / 2), 
				placementWall.getWidth(), placementWall.getHeight(), "grey_tran");
		
	}
	
	public void translatePlacementWall(int type) {
		
		switch (type) {
		case 0 : placementWall.rotateLeft();System.out.println("left"); break;
		case 1 : placementWall.rotateRight();System.out.println("right"); break;
		case 2 : placementWall.scaleUp();System.out.println("up"); break;
		case 3 : placementWall.scaleDown();System.out.println("down"); 
		}
		
	}
	
	public void addEnvironment(MyCanvas canvas, int x, int y) {
		
		environment.add(new Wall(placementWall.getWidth(), placementWall.getHeight(),
							x - (placementWall.getWidth() / 2), y - (placementWall.getHeight() / 2)));
		
		drawArena(canvas);
		
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
		
		switch (type) {
		
		case 0 : 	// Add RandomMover at random location
			Drone drone = new Drone(0, 0, d.random());
			do {
				Random ranGen = new Random();
				x = ranGen.nextInt(SimView.ARENA_WIDTH - drone.getWidth());
				y = ranGen.nextInt(SimView.ARENA_HEIGHT - drone.getHeight());
				System.out.println("(" + x + ", " + y + ")");
			} while (getDroneAt(x, y, drone.getWidth(), drone.getHeight()) != null || 
					getObstacleAt(x, y, drone.getWidth(), drone.getHeight()) != null);
			drone.setXPos(x);
			drone.setYPos(y);
			manyDrones.add(drone);
			break;
			
		case 1 :	// Add AttackDrone at random location
			AttackDrone aDrone = new AttackDrone(0, 0, d.random());
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
			CautiousDrone cDrone = new CautiousDrone(0, 0, d.random());
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
	public boolean canMoveHere(int id, int x, int y, int width, int height) {	
		
		if (x <= 0 || x >= SimView.ARENA_WIDTH - width || y <= 0 || 
				y >= SimView.ARENA_HEIGHT - height) {			
			return false;
		}
		
		if (getDroneAt(id, x, y, width, height) != null) {		
			return false;
		}
		
		if (getObstacleAt(x, y, width, height) != null) {
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
	public boolean killerCanMoveHere(int id, int x, int y, int distance, int width, int height) {
		
		if (x <= 0 || x >= SimView.ARENA_WIDTH - width || y <= 0 || 
				y >= SimView.ARENA_HEIGHT - width) {
			return false;
		}
		
		for (Drone d : manyDrones) {
			
			if (d.getId() == id) continue;
			if (d instanceof AttackDrone && d.isHere(x, y, distance, width, height)) return false;
			if (!(d instanceof AttackDrone) && d.isHere(x, y, distance, width, height)) manyDrones.remove(d);
			
		}
		
		if (getObstacleAt(x, y, width, height) != null) return false;
		
		return true;
		
	}
	
	public Drone isDroneNear(int id, int x, int y, int distance, int width, int height) {
			
		Drone nearbyDrone;
		
		if ((nearbyDrone = getDroneAtDistance(id, x, y, distance, width, height)) != null) {	
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
	public Drone getDroneAt(int id, int x, int y, int width, int height) {
		
		for (Drone d : manyDrones) {
			if (d.getId() == id) continue;
			if (d.isHere(x, y, width, height)) return d;			
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
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public Wall getObstacleAt(int x, int y, int width, int height) {
		
		for (Wall w : environment) {
			if (w.isHere(x, y, width, height)) return w;
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
	public Drone getDroneAtDistance(int id, int x, int y, int distance, int width, int height) {
		
		for (Drone d : manyDrones) {
			if (d.getId() == id) continue;
			if (d.isHere(x, y, distance, width, height)) return d;			
		}
		
		return null;		

	}
	
	public String drawStatus() {
		
		String info = "";
		
		for (Drone d : manyDrones) 
			info += d.toString();
		
		for  (Wall e : environment) {
			info += e.toString();
		}
		
		return info;
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
