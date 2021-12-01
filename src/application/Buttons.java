package application;

import java.io.FileNotFoundException;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Buttons extends HBox {

	private Button addDrone, addAttackDrone, addCautiousDrone, play, stop, addWall;
	
	private AnimationTimer animationTimer, wallAnimation;
	DroneArena arena;
	Canvas canvas;
	MyCanvas myCanvas;
	private boolean wallSelected, animationPlaying;
	
	String buttonStyle = "-fx-background-color: \n"
			+ "        linear-gradient(#f2f2f2, #d6d6d6),\n"
			+ "        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\n"
			+ "        linear-gradient(#dddddd 0%, #f6f6f6 50%);\n"
			+ "    -fx-background-radius: 8,7,6;\n"
			+ "    -fx-background-insets: 0,1,2;\n"
			+ "    -fx-text-fill: black;\n"
			+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";
	String buttonHoverStyle = "-fx-background: darken(red 1.5%);\n"
			+ "  -fx-border: 1px solid rgba(#000, .05);\n"
			+ "  -fx-box-shadow: 1px 1px 2px rgba(#fff, .2);\n"
			+ "  -fx-color: lighten(red, 18%); \n"
			+ "  -fx-text-decoration: none;\n"
			+ "  -fx-text-shadow: -1px -1px 0 darken(red, 9.5%);\n"
			+ "  -fx-transition: all 250ms linear;";
	
	public Buttons(DroneArena arena, MyCanvas myCanvas, Canvas canvas) {
		
		this.arena = arena;
		this.myCanvas = myCanvas;
		this.canvas = canvas;
		//this.setStyle("-fx-background-color: #000000");
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
		
	/**
	 * Add buttons to the HBox
	 */
	private void addButtons() {
		
		Text animation, drones, environment;

		animation = new Text("Animation");
		drones = new Text("Drones");
		environment = new Text("Environment");
		
		Separator one, two;
		one = new Separator(Orientation.VERTICAL);
		two = new Separator(Orientation.VERTICAL);
		
		this.getChildren().addAll(animation, play, stop, one, drones, addDrone, addCautiousDrone, 
									addAttackDrone, two, environment, addWall);
		
	}
	
	/**
	 * Creates an attackDrone, as long as there is a drone of another type available to
	 * 	target, which is not already targeted by another attack drone
	 */
	private void createAddAttackDrone() {
		
		addAttackDrone = new Button("Add Attack Drone");
		addAttackDrone.setStyle(buttonStyle);
		
		addAttackDrone.setOnAction(e -> {
			
			try {
				
				boolean hasOtherDrone = false;
				boolean targetFreeDrone = false; // Check there's a drone without a target
				
				for (Drone d : arena.getDrones()) {
					if (!(d instanceof AttackDrone) && !(d instanceof CautiousDrone) &&
							!d.getIsTarget()) hasOtherDrone = true;					
				}
				
				// Only add drone if there is one available to target
				if (hasOtherDrone) arena.addDrone(1);
				else {System.out.println("no drone to target");}
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			arena.drawArena(myCanvas);
			
		});
		
	}

	
	private void createAddCautiousDrone() {
		
		addCautiousDrone = new Button("Add Cautious Drone");
		addCautiousDrone.setStyle(buttonStyle);
		
		addCautiousDrone.setOnAction(e -> {
			
			try {
				arena.addDrone(2);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			arena.drawArena(myCanvas);
			
		});
		
	}

	/**
	 * Create button to add drone to arena
	 */
	private void createAddDrone() {
		
		addDrone = new Button("Add Drone");
		addDrone.setStyle(buttonStyle);
		
		addDrone.setOnAction(e -> {
			
			try {
				arena.addDrone(0);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			arena.drawArena(myCanvas);
			
		});
		
	}

	private void createAddWall() {
		
		addWall = new Button("Add Wall");
		addWall.setStyle(buttonStyle);	
		
		// Array needed as seperate int not allowed in mouse handler
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
						if (wallSelected && !animationPlaying) {
							arena.addEnvironment(myCanvas, mouse[0], mouse[1]);
							wallSelected = false;			
							wallAnimation.stop();
						}
					});	
					
					if (!animationPlaying)
						arena.drawWallPlacement(myCanvas, mouse[0], mouse[1]);
					
	             }	
			};	
			if (!animationPlaying)
				wallAnimation.start();		
			
		
	     });
	}
	
	/**
	 * Create button to start animation
	 */
	private void createPlay() {
		
		play = new Button("Play");
		play.setStyle(buttonStyle);
		
		play.setOnAction(e -> {
			
			animationPlaying = true;
			
			if (animationTimer != null) {
				animationTimer.stop();
			}			
			
			animationTimer = new AnimationTimer()
	        {
	            @Override
	            public void handle(long now)
	            {
	            	try {
						arena.moveAllDrones();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	arena.drawArena(myCanvas);
	            }		
	        };
	        animationTimer.start();
			
		});
		
	}
	
	/**
	 * Create button to stop animation
	 */
	private void createStop() {
		
		stop = new Button("Stop");
		stop.setStyle(buttonStyle);
		
		stop.setOnAction(e -> {
			
			animationPlaying = false;
			
			animationTimer.stop();
			
		});
		
	}
	
	/**
	 * Set the layout of the buttons
	 */
	private void setButtonLayout() {
		
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(10);
		
	}
}
	
	


