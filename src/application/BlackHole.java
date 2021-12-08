package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class BlackHole extends Environment {
	
	private int centerX;
	private int centerY;
	DroneArena arena;

	public BlackHole(int xPos, int yPos) throws FileNotFoundException {
		
		super(xPos, yPos);
		
		width = 100;
		height = 100;
		centerX = xPos + (width / 2);
		centerY = yPos + (height / 2);
		type = "Black Hole";
		
		
		
	}
	
	public BlackHole(int xPos, int yPos, int width, int height) throws FileNotFoundException {	
		
		super(xPos, yPos);
		
		System.out.println("BlackHole()");
		
		this.width = width;
		this.height = height;
		
		type = "Black Hole";
		
		centerX = xPos + (width / 2);
		centerY = yPos + (height / 2);		
		
		image = new Image(new FileInputStream("graphics/blackHole.png"));
		
	}
	
	protected void checkForDrones(DroneArena arena, ArrayList<Drone> manyDrones) {
		
		Drone nearbyDrone;
		
		if ((nearbyDrone = arena.getDroneAt(centerX, centerY, 70, width, height)) != null) {
			
			System.out.println("Drone nearby");					
			
			int nearbyDroneX = nearbyDrone.getXPos();
			int nearbyDroneY = nearbyDrone.getYPos();
			int nearbyDroneId = nearbyDrone.getId();			
			
			manyDrones.get(nearbyDroneId).setNearHole(true);
			
			System.out.println("drone: " + nearbyDroneX + ", " + nearbyDroneY + 
					" hole: " + xPos + ", " + yPos);
			
			if (nearbyDroneX > xPos) {
				nearbyDroneX-=3;
				System.out.println("x-=4");
			}
			if (nearbyDroneX < xPos) {
				nearbyDroneX+=3;
				System.out.println("x+=4");
			}
			if (nearbyDroneY > yPos) {
				nearbyDroneY-=3;
				System.out.println("y-=4");
			}
			if (nearbyDroneY < yPos) {
				nearbyDroneY+=3;
				System.out.println("y+=4");
			}
			
			manyDrones.get(nearbyDroneId).setXPos(nearbyDroneX);
			manyDrones.get(nearbyDroneId).setYPos(nearbyDroneY);
			
			if (manyDrones.get(nearbyDroneId).getWidth() > 10)
				manyDrones.get(nearbyDroneId).scaleDown(1);
			
			if (nearbyDroneX > (xPos - 4) && nearbyDroneX < (xPos + 4) &&
					nearbyDroneY > (yPos - 4) && nearbyDroneY < (yPos + 4)) {
				manyDrones.remove(nearbyDroneId);
				arena.resetDroneList();
			}
		}
		
	}
	
	@Override
	public boolean isHere(int droneX, int droneY, int droneWidth, int droneHeight) {
		// TODO Auto-generated method stub
		return false;
	}

}
