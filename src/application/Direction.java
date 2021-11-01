package application;

import java.util.Random;

/**
 * Used to specify directions: North, South, East or West
 * @author JoshH
 */
public enum Direction {
	NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;
	
	/**	 
	 * @return next Direction eg WEST then NORTH
	 */
	public Direction next() {
		return values()[(ordinal() + 1) % values().length];
	}
	
	/**
	 * @return random Direction
	 */
	public Direction random() {
		Random generator = new Random();
		return values()[generator.nextInt(values().length)];
	}
}