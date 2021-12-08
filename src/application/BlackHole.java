package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class BlackHole extends Environment {
	
	private int centerX;
	private int centerY;
	private int distance;
	DroneArena arena;

	public BlackHole(int xPos, int yPos) throws FileNotFoundException {
		
		super(xPos, yPos);
		
		width = 100;
		height = 100;
		centerX = xPos + (width / 2);
		centerY = yPos + (height / 2);
		type = "Black Hole";	
		distance = 50;
		
	}
	
	public BlackHole(int xPos, int yPos, int width, int height) throws FileNotFoundException {	
		
		super(xPos, yPos);
		
		System.out.println("BlackHole()");
		
		this.width = width;
		this.height = height;
		
		type = "Black Hole";
		distance = 50;
		
		centerX = xPos + (width / 2);
		centerY = yPos + (height / 2);		
		
		image = new Image(new FileInputStream("graphics/blackHole.png"));
		
	}
	
	/**
	 * Check's it the passed in drone is within distance to be sucked in
	 * @param arena - main drone arena
	 * @param drone - drone to check against
	 */
	public void getIsNear(DroneArena arena, Drone drone) {
		
		if (drone.xPos > (xPos - drone.width - distance) && 
				drone.xPos < (xPos + width + distance) &&
				drone.yPos > (yPos - drone.height - distance) && 
				drone.yPos < (yPos + height + distance)) {
			
			// Use center of drone to compare with center of blackhole
			int droneCenterX = drone.xPos + (drone.height / 2); 
			int droneCenterY = drone.yPos + (drone.width / 2);
			
			// Set drone's nearHole to true
			drone.nearHole = true;
			
			// Move drone toward blackhole
			if (drone.xPos > centerX) {
				drone.xPos -= 3;
				System.out.println("x-=4");
			}
			if (drone.xPos < centerX) {
				drone.xPos += 3;
				System.out.println("x+=4");
			}
			if (drone.yPos > centerY) {
				drone.yPos -= 3;
				System.out.println("y-=4");
			}
			if (drone.yPos < centerY) {
				drone.yPos += 3;
				System.out.println("y+=4");
			}
			
			if (drone.width > 10)
				drone.scaleDown(1);
			
			if (droneCenterX > (centerX - 4) && 
					droneCenterX < (centerX + 4) &&
						droneCenterX > (centerY - 4) && 
							droneCenterY < (centerY + 4)) {
				arena.removeDroneFromList(drone.id);
				arena.resetDroneList();
			}
		}
		
	}

	/**
	 * @return distance from the blackhole where a drone's sucked in
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * @return x co-ord of center of blackhole
	 */
	public int getCenterX() {
		return centerX;
	}
	
	/**
	 * @return y co-ord of center of blackhole
	 */
	public int getCenterY() {
		return centerY;
	}
	
	@Override
	public boolean isHere(int droneX, int droneY, int droneWidth, int droneHeight) {
		// TODO Auto-generated method stub
		return false;
	}

}
