package application;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Buttons extends HBox {

	private Button addDrone, addAttackDrone, play, stop;
	
	MyCanvas canvas;
	DroneArena arena;
	private AnimationTimer animationTimer;
	
	public Buttons(DroneArena arena, MyCanvas canvas) {
		
		this.arena = arena;
		this.canvas = canvas;
		
		createAddDrone();
		createAddAttackDrone();
		createPlay();
		createStop();
		addButtons();
		setButtonLayout();
		
	}
	
	/**
	 * Set the layout of the buttons
	 */
	private void setButtonLayout() {
		
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		
	}
	
	/**
	 * Add buttons to the HBox
	 */
	private void addButtons() {
		
		this.getChildren().addAll(addDrone, addAttackDrone, play, stop);
		
	}
	
	/**
	 * Create button to stop animation
	 */
	private void createStop() {
		
		stop = new Button("Stop");
		
		stop.setOnAction(e -> {
			
			animationTimer.stop();
			
		});
		
	}

	/**
	 * Create button to start animation
	 */
	private void createPlay() {
		
		play = new Button("Play");
		
		play.setOnAction(e -> {
			
			if (animationTimer != null) {
				animationTimer.stop();
			}			
			
			animationTimer = new AnimationTimer()
	        {
	            @Override
	            public void handle(long now)
	            {
	            	arena.moveAllDrones();
	            	arena.drawArena(canvas);
	            }		
	        };
	        animationTimer.start();
			
		});
		
	}

	/**
	 * Create button to add drone to arena
	 */
	private void createAddDrone() {
		
		addDrone = new Button("Add Drone");
		
		addDrone.setOnAction(e -> {
			
			arena.addDrone(0);
			arena.drawArena(canvas);
			
		});
		
	}
	
	private void createAddAttackDrone() {
		
		addAttackDrone = new Button("Add Attack Drone");
		
		addAttackDrone.setOnAction(e -> {
			
			arena.addDrone(1);
			arena.drawArena(canvas);
			
		});
		
	}
	
}
