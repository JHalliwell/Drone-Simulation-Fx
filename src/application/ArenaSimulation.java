package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class ArenaSimulation {
	private Timeline timeline;
	private DroneArena droneArena;
	
	public ArenaSimulation(DroneArena droneArena) {
		this.timeline = new Timeline(new KeyFrame(Duration.millis(200), this::doStep));
		this.droneArena = droneArena;
	}
	
	private void doStep(ActionEvent actionEvent) {
		
	}
}
