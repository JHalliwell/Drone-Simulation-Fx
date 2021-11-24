package application;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Buttons extends HBox {

	private Button addDrone, addAttackDrone, addCautiousDrone, play, stop, addWall;
	
	Canvas canvas;
	MyCanvas myCanvas;
	DroneArena arena;
	private AnimationTimer animationTimer, wallAnimation;
	private boolean wallSelected, animationPlay;
	
	public Buttons(DroneArena arena, MyCanvas myCanvas, Canvas canvas) {
		
		this.arena = arena;
		this.myCanvas = myCanvas;
		this.canvas = canvas;
		
		createAddDrone();
		createAddAttackDrone();
		createAddCautiousDrone();
		createPlay();
		createStop();
		createAddWall();
		addButtons();
		setButtonLayout();
		
		canvas.setFocusTraversable(true);
		
		
		
				
									
			
	}
		
//		canvas.setOnKeyPressed(e -> {
//			
//			if (e.getCode() == KeyCode.LEFT) {
//				arena.translatePlacementWall(0);
//				System.out.println("left");
//			}
//			if (e.getCode() == KeyCode.RIGHT) {
//				arena.translatePlacementWall(1);
//				System.out.println("right");
//			}
//			if (e.getCode() == KeyCode.UP) {
//				arena.translatePlacementWall(2);
//				System.out.println("up");
//			}
//			if (e.getCode() == KeyCode.DOWN) {
//				arena.translatePlacementWall(3);
//				System.out.println("down");
//			}
//			
//		});
		
		
						
	
	
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
		
		this.getChildren().addAll(addDrone, addAttackDrone, addCautiousDrone, play, stop, addWall);
		
	}

	
	/**
	 * Create button to stop animation
	 */
	private void createStop() {
		
		stop = new Button("Stop");
		
		stop.setOnAction(e -> {
			
			animationPlay = false;
			
			animationTimer.stop();
			
		});
		
	}

	/**
	 * Create button to start animation
	 */
	private void createPlay() {
		
		play = new Button("Play");
		
		play.setOnAction(e -> {
			
			animationPlay = true;
			
			if (animationTimer != null) {
				animationTimer.stop();
			}			
			
			animationTimer = new AnimationTimer()
	        {
	            @Override
	            public void handle(long now)
	            {
	            	arena.moveAllDrones();
	            	arena.drawArena(myCanvas);
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
			arena.drawArena(myCanvas);
			
		});
		
	}
	
	private void createAddAttackDrone() {
		
		addAttackDrone = new Button("Add Attack Drone");
		
		addAttackDrone.setOnAction(e -> {
			
			arena.addDrone(1);
			arena.drawArena(myCanvas);
			
		});
		
	}
	
	private void createAddCautiousDrone() {
		
		addCautiousDrone = new Button("Add Cautious Drone");
		
		addCautiousDrone.setOnAction(e -> {
			
			arena.addDrone(2);
			arena.drawArena(myCanvas);
			
		});
		
	}
	
	private void createAddWall() {
		
		addWall = new Button("Add Wall");
		
		
		
		int[] mouse = new int[2];
		
		addWall.setOnAction(e -> {
			
			wallSelected = true;
			
			wallAnimation = new AnimationTimer()
			{
				@Override
	            public void handle(long now)
	            {
								
					canvas.setOnMouseMoved(e -> {						
						mouse[0] = (int)e.getX();
						mouse[1] = (int)e.getY();
					});
																
					canvas.setOnMouseClicked(e -> {
						if (wallSelected && !animationPlay) {
							arena.addEnvironment(myCanvas, mouse[0], mouse[1]);
							wallSelected = false;			
							wallAnimation.stop();
						}
					});	
					
					arena.drawWallPlacement(myCanvas, mouse[0], mouse[1]);
					
	             }	
			};			
			wallAnimation.start();		
			
		
	     });
	}
}
	
	


