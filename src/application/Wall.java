package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import javafx.scene.image.Image;

public class Wall extends Environment {
	
	private static final long serialVersionUID = 223632663054522703L;

	public Wall(int xPos, int yPos, int width, int height) throws FileNotFoundException {
		
		super(xPos, yPos);
		
		this.width = width;
		this.height = height;
		type = "Barrier";

		image = new Image(new FileInputStream("graphics/wallV.png"));

	}
	
	public Wall(int xPos, int yPos) throws FileNotFoundException {
		
		super(xPos, yPos);
		
		width = 100;
		height = 10;

		image = new Image(new FileInputStream("graphics/wallV.png"));
		
		System.out.print(width + " " + height);
		
	}

	public String getColour() {
		
		return "darkGrey";
		
	}
		
	/**
	 * 
	 * @param droneX	drone xPos
	 * @param droneY	drone yPos
	 * @param droneWidth
	 * @param droneHeight
	 * @return True if drone overlaps with obstacle
	 */
	public boolean isHere(int droneX, int droneY, int droneWidth, int droneHeight) {
		
		if (droneX > (xPos - droneWidth - 2) && 
				droneX < (xPos + width + 2) &&
				droneY > (yPos - droneHeight - 2) && 
				droneY < (yPos + height + 2)) return true;			
		
		return false;
		
	}
	
	@Override
	public boolean isHereEnvironmentPlacement(int otherX, int otherY, int otherWidth, int otherHeight) {
		
		if (otherX > (xPos - (otherWidth / 2) - 2) && 
				otherX < (xPos + width + (otherWidth / 2) - 2) &&
				otherY > (yPos - (otherHeight / 2) - 2 ) && 
				otherY < (yPos + height + (otherHeight / 2) + 2)) 
			return true;
		
		return false;
	}
	
	public void rotate() {		

		int temp = width;
		width = height;
		height = temp;		
		
	}
	
	public void scaleDown() {
		
		if (width > 10 && height > 10) {
			width *= 0.75;
			height *= 0.75;
		}

	}
	
	public void scaleUp() {
		
		if (width < 800 && height < 800) {
			width *= 1.25;
			height *= 1.25;
		}

	}


	
}
