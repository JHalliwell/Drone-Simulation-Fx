package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DroneArena implements Serializable {

	private static final long serialVersionUID = -4938436733718179739L;
	private MyCanvas myCanvas;
	private int arenaWidth;
	private int arenaHeight;
	
	private ArrayList<Environment> environment;
	private ArrayList<Drone> manyDrones;
	
	/**
	 *  Creates arrayList of drones and a canvas on which to draw arena
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
	public void addDrone(int type) throws FileNotFoundException {     // keep
		
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
					getObstacleAt(x, y, drone.getWidth(), drone.getHeight()) != null);
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
					getObstacleAt(x, y, aDrone.getWidth(), aDrone.getHeight()) != null);
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
	
	public void drawEnvironmentPlacement(MyCanvas canvas, int x, int y, String colour, 
											Environment e) {
		
		canvas.clear();		
		
		drawArena(canvas);
		
		if (e instanceof Wall) {
			
			canvas.drawObject(x - (e.getWidth() / 2), y - (e.getHeight() / 2), 
					e.getWidth(), e.getHeight(), colour);
			
		}
		
		if (e instanceof BlackHole) {
			
			canvas.drawObject(x - (e.getWidth() / 2), y - (e.getHeight() / 2), 
					e.getWidth(), e.getHeight(), colour);
			
		}		
		
	}
	
	public void addEnvironment(MyCanvas canvas, int xPos, int yPos, Environment e) 
								throws FileNotFoundException {	

		if (e instanceof Wall) {
			
			environment.add(new Wall(xPos - (e.getWidth() / 2), 
					yPos - (e.getHeight() / 2), e.getWidth(),
					e.getHeight()));
			
		}
		
		if (e instanceof BlackHole) {
			
			System.out.println("Add environment");
			
			environment.add(new BlackHole(xPos - (e.getWidth() / 2), 
					yPos - (e.getHeight() / 2), e.getWidth(),
					e.getHeight()));
			
		}		

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
	
	public String debugStatus() {	// keep
		
		String info = "";
		
		for (Drone d : manyDrones) 
			 System.out.println(d.toString());
		
		for  (Environment e : environment) {
			System.out.println(e.toString());
		}
		
		return info;
	}
	
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
	 * Search arrayList of drones to see if there's one at x,y
	 * @param x		drone x pos
	 * @param y		drone y pos
	 * @return null if no Drone there, or Drone if there is
	 */
	public Drone getDroneAtEnvironmentPlacement(int x, int y, int width, int height) {
		
		for (Drone d : manyDrones) {
			if (d.isHereEnvironmentPlacement(x, y, width, height)) return d;
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
	
	public void removeDroneFromList(int id) {
		
		manyDrones.remove(id);
		
	}
	
	public void addEnvironmentToList(int xPos, int yPos, int width, 
										int height, String type) 
												throws FileNotFoundException {
		if (type == "wall") {
			environment.add(new Wall(xPos, yPos, width, height));
		}
	}
	
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
		
		for (Drone dr : manyDrones) {
			if (dr instanceof AttackDrone) {
				((AttackDrone) dr).setTarget(this);
			}
		}
		
		setDrones(manyDrones); // Set arena list to edited list
		Drone.droneCount = manyDrones.size();
		drawStatus();
		
	}
	
	public void clearEnvironment() {
		environment.clear();
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
		return manyDrones;		
	}
	
	/**
	 * @param id
	 * @return	drone of specified id
	 */
	public Drone getDrone(int id) {
		return manyDrones.get(id);
	}
	
	public ArrayList<Environment> getEnvironment(){
		return environment;
	}
	
	public void setDrones(ArrayList<Drone> manyDrones) {		
		this.manyDrones = manyDrones;		
	}	
	
	public void setEnvironment(ArrayList<Environment> environment) {
		this.environment = environment;
	}
	
	public int getWidth() {
		return arenaWidth;
	}
	
	public int getHeight() {
		return arenaHeight;
	}

}
