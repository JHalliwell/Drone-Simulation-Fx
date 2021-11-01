package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ArenaGrid {
	private String[][] arenaGrid;

	/**
	 * @param x		x dimension of arena size, inside border
	 * @param y		y dimension of arena size, inside border
	 */
	ArenaGrid(int x, int y) {
		arenaGrid = new String[x][y];		
	}
	
	/**
	 * Adds a character 'c' to the canvas array, +1 to account for border
	 * @param x 	x co-ord of c
	 * @param y		y co-ord of c
	 * @param c		the character to add to canvas
	 */
	public void showIt(int x, int y, String c) {
		arenaGrid[x][y] = c;
	}
	
	public void drawDrones(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		for (int i = 0; i < SimView.ARENA_WIDTH; i++) {
			for (int j = 0; j < SimView.ARENA_HEIGHT; j++) {				
				gc.fillText(arenaGrid[i][j], i + SimView.ARENA_LEFT_BORDER, j + SimView.ARENA_TOP_BOREDER);
			}
		}
	}
}
