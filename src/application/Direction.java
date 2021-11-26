package application;

import java.util.Random;

/**
 * Used to specify directions: North, South, East or West
 * @author JoshH
 */
public enum Direction {
	EAST, NORTH, NORTH_EAST, NORTH_WEST, SOUTH, SOUTH_EAST, SOUTH_WEST, WEST;
	
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