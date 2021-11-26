package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;

public class Drone implements Serializable {
	
	protected static int count = 0;
	protected String colour;
	protected Direction direction;
	
	protected ImageView droneImageView;
	protected Image droneImage;
	protected MyCanvas myCanvas;
	protected int height = 50;
	protected int width = 50;
	protected int x, y, id, dx, dy, allowedDistance;
	
	/**
	 * Construct drone at position x,y
	 * @param x		drones x position
	 * @param y		drones y position
	 * @param d		direction of drone
	 * @throws FileNotFoundException 
	 */
public Drone(int x, int y, Direction d, MyCanvas myCanvas) throws FileNotFoundException {
		
		this.myCanvas = myCanvas;
		this.x = x;
		this.y = y;
		this.allowedDistance = 2;
		colour = "black";			
		
		direction = d.random();
		id = count++;
		this.setDirection();
		

		
	}	

    public String getColour() {
		return colour;
	}	
	
	public int getHeight() {
		return height;
	}	

	public int getId() {
		return this.id;
	}	
	
	public Image getImage() {
		return droneImage;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getXPos() {
		return this.x;
	}
	
	public int getXSpeed() {
		return this.dx;
	}
	
	public int getYPos() {
		return this.y;
	}
	
	public int getYSpeed() {
		return this.dy;
	}
	
	/**
	 * 
	 * @param otherX
	 * @param otherY
	 * @param otherWidth
	 * @param otherHeight
	 * @return True if both drones overlap
	 */
	public boolean isHere(int otherX, int otherY, int otherWidth, int otherHeight) {
		
		if (otherX > (this.x - otherWidth - 2) && 
				otherX < (this.x + otherWidth + 2) &&
				otherY > (this.y - otherHeight - 2) && 
				otherY < (this.y + otherHeight + 2)) return true;			
		
		return false;
		
	}
	
	/**
	 * Is the drone at this x,y position?
	 * @param x		x position
	 * @param y		y position
	 * @return		true if drone is at x,y. False otherwise
	 */
	public boolean isHere(int otherX, int otherY, int distance, int otherWidth, int otherHeight) {
		
		if (otherX > (this.x - otherWidth - distance) && 
				otherX < (this.x + otherWidth + distance) &&
				otherY > (this.y - otherHeight - distance) && 
				otherY < (this.y + otherHeight + distance)) return true;			
		
		return false;
		
	}
	
	/**
	 * Change dx and dy to correspond to Direction enum
	 * @throws FileNotFoundException 
	 */
	public void setDirection() throws FileNotFoundException {	
		
		if (this.direction == direction.NORTH) {
			droneImage = new Image(new FileInputStream("graphics/regDroneN.png"));
			dx = 0;
			dy = -2;
		}
		if (this.direction == direction.NORTH_EAST) {
			droneImage = new Image(new FileInputStream("graphics/regDroneNE.png"));
			dx = 2;
			dy = -2;
		}
		if (this.direction == direction.EAST) {
			droneImage = new Image(new FileInputStream("graphics/regDroneE.png"));
			dx = 2;
			dy = 0;
		}
		if (this.direction == direction.SOUTH_EAST) {
			droneImage = new Image(new FileInputStream("graphics/regDroneSE.png"));
			dx = 2;
			dy = 2;
		}
		if (this.direction == direction.SOUTH) {
			droneImage = new Image(new FileInputStream("graphics/regDroneS.png"));
			dx = 0;
			dy = 2;
		}
		if (this.direction == direction.SOUTH_WEST) {
			droneImage = new Image(new FileInputStream("graphics/regDroneSW.png"));
			dx = -2;
			dy = 2;
		}
		if (this.direction == direction.WEST) {
			droneImage = new Image(new FileInputStream("graphics/regDroneW.png"));
			dx = -2;
			dy = 0;
		}
		if (this.direction == direction.NORTH_WEST) {
			droneImage = new Image(new FileInputStream("graphics/regDroneNW.png"));
			dx = -2;
			dy = -2;
		}
		
	}
	
	public void setXPos(int x) {
		this.x = x;
	}
	
	public void setYPos(int y) {
		this.y = y;		
	}
	
	public String toString() {
		
		String info = "";
		
		info += id + " -- Position: (" + x + ", " + y + ")" + "\n";
		
		return info;
		
	}
	
	/**
	 * Try to move drone, check with canMoveHere, if cant, change direction
	 * if can, change x and y
	 * @param arena
	 * @throws FileNotFoundException 
	 */
	public void tryToMove(DroneArena arena) throws FileNotFoundException {

		int newx = x + dx;
		int newy = y + dy;
		int count = 0; // To see if all directions have been tried		
		
		while (!arena.canMoveHere(this.id, newx, newy, width, height)) {			
			if (count > 8) break;	// If the drone can't move anywhere, stop trying to move	
						
			this.direction = direction.random();	// Move to next direction			
			this.setDirection();	// Set dx,dy from direction
			newx = x + dx;
			newy = y + dy;			
			count++;
		};
		
		// Only move if drone can move to 'empty' location
		if (count <= 8) {			
			x = newx;
			y = newy;
		}
		
	}
	
}
