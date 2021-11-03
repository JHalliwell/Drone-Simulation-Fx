package application;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;

public class ArenaSimulation {
	private Timeline timeline;
	private DroneArena droneArena;
	private Canvas simCanvas;
	private SimView simView;
	
	private AnimationTimer animationTimer;
	
	public ArenaSimulation() {
			
	}
	

