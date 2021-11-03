package application;

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
	
	public ArenaSimulation(DroneArena droneArena, Canvas canvas) {
		timeline = new Timeline(new KeyFrame(Duration.millis(50), this::doStep));
		timeline.setCycleCount(Timeline.INDEFINITE);
		this.droneArena = droneArena;
		this.simCanvas = canvas;
		simView = new SimView();
	}
	
	private void doStep(ActionEvent actionEvent) {
		droneArena.moveAllDrones();
		simView.drawDrones();
	}
	
	public void stop() {
		timeline.stop();
	}
	
	public void play() {
		timeline.play();
	}
}
