package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ArenaCanvas {
	private String[][] canvas;

	/**
	 * @param x		x dimension of arena size, inside border
	 * @param y		y dimension of arena size, inside border
	 */
	ArenaCanvas(int x, int y) {
		int newx = x + 2;
		int newy = y + 2; // +2 to account for border
		canvas = new String[newx][newy];		
	}
	
	/**
	 * Adds a character 'c' to the canvas array, +1 to account for border
	 * @param x 	x co-ord of c
	 * @param y		y co-ord of c
	 * @param c		the character to add to canvas
	 */
	public void showIt(int x, int y, String c) {
		canvas[x + 1][y + 1] = c;
	}
	
	public void drawDrones(GraphicsContext graphicsContext) {
		graphicsContext.setFill(Color.BLACK);
		for (int i = 0; i < DroneArena.ARENA_WIDTH; i++) {
			for (int j = 0; j < DroneArena.ARENA_HEIGHT; j++) {				
				graphicsContext.fillText(canvas[i][j], i, j);
			}
		}
	}
}
