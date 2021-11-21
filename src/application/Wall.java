package application;

public class Wall {
	
	int height, width, xPos, yPos;
	String colour;
	
	public Wall(int width, int height, int xPos, int yPos) {
		
		this.width = width;
		this.height = height;		
		this.xPos = xPos;
		this.yPos = yPos;
		
		colour = "black";
		
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public String getColour() {
		return colour;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
}
