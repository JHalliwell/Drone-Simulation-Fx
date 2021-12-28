package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles informaion and methods on the arena, which includes
 * 'drones' and environment objects
 * @author JoshH
 *
 */
public class DroneArena implements Serializable {

	private static final long serialVersionUID = -4938436733718179739L;
	private MyCanvas myCanvas;
	private int arenaWidth;
	private int arenaHeight;
	
	private ArrayList<Environment> environment;
	private ArrayList<Drone> manyDrones;
	
	/**
	 * @param myCanvas - Handles methods for drawing on canvas
	 * @param arenaWidth - Width of arena
	 * @param arenaHeight - Height of arena
	 * @throws IOException
	 */
	DroneArena(MyCanvas myCanvas, int arenaWidth, int arenaHeight) throws IOException {		
		this.myCanvas = myCanvas;
		this.arenaWidth = arenaWidth;
		this.arenaHeight = arenaHeight;
		
		manyDrones = new ArrayList<Drone>();	
		environment = new ArrayList<Environment>();	
	}

	/**
	 * Adds a drone to the arena in random area moving random direction,
	 * adds to arrayList of drones, ensuring no other drone at location
	 * @param	type of drone to add 
	 * @throws FileNotFoundException 
	 */
	public void addDrone(int type) throws FileNotFoundException {   		
		int x;
		int y;
		Direction d = Direction.EAST; // Initialise, but will be made random later
		
		switch (type) {		
		case 0 : 	// Add RandomMover at random location
			RoamDrone drone = new RoamDrone(0, 0, d.random(), myCanvas);
			do {
				Random ranGen = new Random();
				x = ranGen.nextInt(arenaWidth - drone.getWidth());
				y = ranGen.nextInt(arenaHeight - drone.getHeight());
			} while (getDroneAt(x, y, drone.getWidth(), drone.getHeight()) != null || 
					getEnironmentAt(x, y, drone.getWidth(), drone.getHeight()) != null ||
					getHoleAt(x, y, drone.getWidth(), drone.getHeight()) != null);
			drone.setXPos(x);
			drone.setYPos(y);
			manyDrones.add(drone);
			break;
			
		case 1 :	// Add AttackDrone at random location
			AttackDrone aDrone = new AttackDrone(0, 0, d.random(), myCanvas, this);
			do {
				Random ranGen = new Random();
				x = ranGen.nextInt(arenaWidth - aDrone.getWidth());
				y = ranGen.nextInt(arenaHeight - aDrone.getHeight());
			} while (getDroneAt(x, y, aDrone.getWidth(), aDrone.getHeight()) != null || 
					getEnironmentAt(x, y, aDrone.getWidth(), aDrone.getHeight()) != null ||
					getHoleAt(x, y, aDrone.getWidth(), aDrone.getHeight()) != null);
			aDrone.setXPos(x);
			aDrone.setYPos(y);
			manyDrones.add(aDrone);
			break;
			
		case 2 :	// Add CautiousDrone at random location
			CautiousDrone cDrone = new CautiousDrone(0, 0, d.random(), myCanvas);
			do {
				Random ranGen = new Random();
				x = ranGen.nextInt(arenaWidth - cDrone.getWidth());
				y = ranGen.nextInt(arenaHeight - cDrone.getHeight());
			} while (getDroneAt(x, y, cDrone.getWidth(), cDrone.getHeight()) != null || 
					getEnironmentAt(x, y,cDrone.getWidth(), cDrone.getHeight()) != null ||
					getHoleAt(x, y, cDrone.getWidth(), cDrone.getHeight()) != null);
			cDrone.setXPos(x);
			cDrone.setYPos(y);
			manyDrones.add(cDrone);
			break;
		
		}		
	}
	
	/**
	 * Loop through all drones, moving them each once, unless they
	 * are within the field of a blackhole
	 * @throws FileNotFoundException 
	 */
	public void moveAllDrones() throws FileNotFoundException {			
		for (Drone d : manyDrones) {	
			d.checkForHole(this);
			if (!d.nearHole) d.tryToMove(this);
		}		
	}
	
	/**
	 * Draws drones and environment objects to canvas as graphics context
	 */
	public void drawArena(MyCanvas canvas) {	  
		canvas.clear();
        
        for (Drone d : manyDrones) {        	
    		canvas.drawImage(d.getImage(), d.getXPos(), d.getYPos(), d.getPrintWidth(), d.getPrintHeight());      		
        }
       
        for (Environment e : environment) {        	
        	if (e instanceof Wall) canvas.drawObject(e.getXPos(), e.getYPos(), e.getWidth(), 
        												e.getHeight(), e.getColour());
        	
        	if (e instanceof BlackHole) {        		
        		canvas.drawImage(e.getImage(), e.getXPos(), e.getYPos(),
        							e.getWidth(), e.getHeight()); 
        		        		
        		// Draw a circle the size of the blackhole's field
        		canvas.drawObject(e.getXPos() - ((BlackHole) e).getDistance(), e.getYPos() - 
        							((BlackHole) e).getDistance(), 
        							(e.getWidth()) + (((BlackHole) e).getDistance() * 2), 
        							(e.getHeight()) + (((BlackHole) e).getDistance() * 2), "hole");	
        	}		      	          	
        }            
	}
	
	/**
	 * For drawing selected object to be placed on the canvas
	 * @param canvas - on which to draw
	 * @param xPos - of object
	 * @param yPos - of object
	 * @param type - determines what to draw
	 * @param e - the object to draw
	 */
	public void drawEnvironmentPlacement(MyCanvas canvas, int x, int y, String type, 
											Environment e) {		
		canvas.clear();		
		
		drawArena(canvas);
		
		if (e instanceof Wall) {
			
			// Draw wall to be placed
			canvas.drawObject(x - (e.getWidth() / 2), y - (e.getHeight() / 2), 
					e.getWidth(), e.getHeight(), type);
			
		}
		
		if (e instanceof BlackHole) {
			
			// Draw circle the size of the blackhole's field
			if (type == "hole") {
				canvas.drawObject(x - (e.getWidth() / 2) - ((BlackHole) e).getDistance(), 
									y - (e.getHeight() / 2) - ((BlackHole) e).getDistance(), 
									e.getWidth() + (((BlackHole) e).getDistance() * 2), 
									e.getHeight() + (((BlackHole) e).getDistance() * 2), type);
			}			
		}			
	}
	
	/**
	 * Adds environment to canvas at given coordinates, from user placement
	 * @param canvas - On which to draw object
	 * @param xPos - of environment object
	 * @param yPos - of environment object
	 * @param e - environment object to draw
	 * @throws FileNotFoundException
	 */
	public void addEnvironmentPlacement(MyCanvas canvas, int xPos, int yPos, Environment e) 
								throws FileNotFoundException {	
		if (e instanceof Wall) {			
			environment.add(new Wall(xPos - (e.getWidth() / 2), 
					yPos - (e.getHeight() / 2), e.getWidth(),
					e.getHeight()));			
		}
		
		if (e instanceof BlackHole) {			
			environment.add(new BlackHole(xPos - (e.getWidth() / 2), 
					yPos - (e.getHeight() / 2), e.getWidth(),
					e.getHeight()));			
		}		

		drawArena(canvas);				
	}
	
	/**
	 * Add an environment object to the list
	 * @param xPos - of environment object to add
	 * @param yPos - of environment object to add
	 * @param width - of environment object to add
	 * @param height - of environment object to add
	 * @param type - of environemt object to add
	 * @throws FileNotFoundException
	 */
	public void addEnvironmentToList(int xPos, int yPos, int width, 
										int height, String type) 
												throws FileNotFoundException {
		if (type == "wall") {
			environment.add(new Wall(xPos, yPos, width, height));
		}
		
		if (type == "blackhole") {
			environment.add(new BlackHole(xPos, yPos));
		}
	}
	
	/**
	 * @return information on all drone's and environment objects
	 */
	public String getStatus() {		
		String info = "";
		
		for (Drone d : manyDrones) 
			info += d.toString();
		
		for  (Environment e : environment) 
			info += e.toString();
				
		return info;
	}
	
	/**
	 * @return Information on each drone and environment object as a
	 * seperate string, in an array list
	 */
	public ArrayList<String> describeAll() {
		
		ArrayList<String> info = new ArrayList<String>();
		for (Drone d : manyDrones) info.add(d.toString());
		
		if (manyDrones.size() > 0 || environment.size() > 0) {
			info.add("\n-------------------------------------"
					+ "----------------\n");
		}		
		
		for (Environment e : environment) info.add(e.toString());
		return info;
		
	}
	
	/**
	 * Adds a drone to the list
	 * @param xPos - of drone to add
	 * @param yPos - of drone to add
	 * @param direction - of drone to add
	 * @param myCanvas - canvas to draw drone
	 * @param type - type of drone to add
	 * @throws FileNotFoundException
	 */
	public void addDroneToList(int xPos, int yPos, Direction direction, 
									MyCanvas myCanvas, String type) 
											throws FileNotFoundException {
		if (type == "roam") {
			manyDrones.add(new RoamDrone(xPos, yPos, direction, myCanvas));
		}
		if (type == "attack") {
			manyDrones.add(new AttackDrone(xPos, yPos, direction, myCanvas, this));
		}
		if (type == "cautious") {
			manyDrones.add(new CautiousDrone(xPos, yPos, direction, myCanvas));
		}
	}
	
	/**
	 * Remove a drone of given id from the list
	 * @param id - of drone to remove
	 */
	public void removeDroneFromList(int id) {		
		manyDrones.remove(id);		
	}
	
	/**
	 * Clear all drones from the list
	 */
	public void clearDrones() {
		manyDrones.clear();
		Drone.droneCount = 0;
	}
	
	/**
	 * To be used after a drone is removed, matches id's to index's
	 * and updates count
	 */
	public void resetDroneList() {
		
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
		
		// Ensure there aren't more attack drone's than roam drones
		int roamCount = 0;
		int attackCount = 0;
		
		for (Drone dr : manyDrones) {			
			if (dr instanceof RoamDrone) roamCount++;
			if (dr instanceof AttackDrone) attackCount++;
		}
		
		while(attackCount > roamCount) {
			for (Drone dr : manyDrones) {
				if (dr instanceof AttackDrone) {
					manyDrones.remove(dr.getId());
				}
			}
			attackCount--;
		}
		
		for (Drone dr : manyDrones) {
			if (dr instanceof AttackDrone) {
				((AttackDrone) dr).setTarget(this);
			}
		}
		
		setDrones(manyDrones); // Set arena list to edited list
		Drone.droneCount = manyDrones.size();
		getStatus();
		
	}
	
	/**
	 * Removes all environment objects from the list
	 */
	public void clearEnvironment() {
		environment.clear();
	}
	
	/**
	 * Search arrayList of drones to see if there's one at x,y
	 * @param xPos - to check list
	 * @param yPos - to check list 
	 * @return null if no Drone there, or Drone if there is
	 */
	public Drone getDroneAtEnvironmentPlacement(int xPos, int yPos, int width, int height) {		
		for (Drone d : manyDrones) {
			if (d.isHereEnvironmentPlacement(xPos, yPos, width, height)) return d;
		}
		
		return null;
	}
	
	/**
	 * Search arrayList of drones to see if there's one at x,y
	 * @param xPos - to check list
	 * @param yPos - to check list
	 * @return null if no Drone there, or Drone if there is
	 */
	public Drone getDroneAt(int xPos, int yPos, int width, int height) {		
		for (Drone d : manyDrones) {
			if (d.isHere(xPos, yPos, width, height)) return d;
		}
		
		return null;
	}
	
	/**
	 * Loop through all drones, returning a drone if it's at newX, newY
	 * @param newX - xPos the drone is trying to move to
	 * @param newY - yPos the drone is trying to move to 
	 * @param arena - main droneArena
	 * @return	Drone if that drone is colliding with newX, newY
	 */
	public Drone getDroneAt(int xPos, int yPos, int distance, int width, int height) {		
		for (Drone d : manyDrones) {
			if (d.isHere(xPos, yPos, distance, width, height)) return d;
		}
		
		return null;		
	}
	
	/**
	 * Search list of environment object for one at given position
	 * @param xPos - to check list for
	 * @param yPos - to check list for
	 * @param width - of object to check against
	 * @param height - of object to check against
	 * @return Environment if there is one found at position, else null
	 */
	public Environment getEnironmentAt(int xPos, int yPos, int width, int height) {		
		for (Environment e : environment) {
			if (e.isHere(xPos, yPos, width, height) && e instanceof Wall) return e;

		}		
		return null;		
	}
	
	/**
	 * Search list of environment object for one at given position, for when user
	 * has selected an environment object to place
	 * @param xPos - to check list for
	 * @param yPos - to check list for
	 * @param width - of object to check against
	 * @param height - of object to check against
	 * @return Environment if there is one found at position, else null
	 */
	public Environment getEnvironmentAtPlacement(int xPos, int yPos, int width, int height) {		
		for (Environment e : environment) {
			if (e.isHereEnvironmentPlacement(xPos, yPos, width, height))
				return e;
		}
		
		return null;
	}
	
	/**
	 * Search list of environment objects for a hole at the given position
	 * @param xPos - to check list for
	 * @param yPos - to check list for
	 * @param width - of object to check against
	 * @param height - of object to check against
	 * @return Environment if there is one found at position, else null
	 */
	public Environment getHoleAt(int xPos, int yPos, int width, int height) {	
		for (Environment e : environment) {
			if (e.isHere(xPos, yPos, width, height) && e instanceof BlackHole)
				return e;
		}
		
		return null;		
	}
	
	/** 
	 * @return this arena
	 */
	public DroneArena getArena() {		
		return this;		
	}
	
	/**
	 * @return	list of drones
	 */
	public ArrayList<Drone> getDrones() {		
		return manyDrones;		
	}
	
	/**
	 * @param id - of drone to return
	 * @return drone of specified id
	 */
	public Drone getDrone(int id) {
		return manyDrones.get(id);
	}
	
	/**
	 * @return environment array list
	 */
	public ArrayList<Environment> getEnvironment() {
		return environment;
	}
	
	/**
	 * @param manyDrones - to set drone array list as
	 */
	public void setDrones(ArrayList<Drone> manyDrones) {		
		this.manyDrones = manyDrones;		
	}	
	
	/**
	 * @param environment - to set environment array list as
	 */
	public void setEnvironment(ArrayList<Environment> environment) {
		this.environment = environment;
	}
	
	/**
	 * @return arenaWidth
	 */
	public int getWidth() {
		return arenaWidth;
	}
	
	/**
	 * @return arenaHeight
	 */
	public int getHeight() {
		return arenaHeight;
	}

}
