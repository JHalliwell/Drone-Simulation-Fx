package application;

import java.io.FileNotFoundException;
import java.time.Duration;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Buttons extends HBox {

	private Button addDrone, addAttackDrone, addCautiousDrone, play, stop, addWall;
	
	private AnimationTimer animationTimer, wallAnimation;
	DroneArena arena;
	Canvas canvas;
	MyCanvas myCanvas;
	BorderPane simPane;
	private boolean wallSelected, animationPlaying, invalidPlacement, placeWall, mouseReleased, mouseClicked;
	private int mouseX, mouseY;
	
	public Buttons(DroneArena arena, MyCanvas myCanvas, Canvas canvas, 
						BorderPane simPane) {
		
		this.simPane = simPane;
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
		
		addAttackDrone = new Button("Honing");		
		//addAttackDrone.setStyle(buttonHoverStyle);
		
		addAttackDrone.setOnAction(e -> {
			
			try {				
				boolean hasOtherDrone = false;
				
				// Only add drone if there is one available to target
				for (Drone d : arena.getDrones()) {
					if (d instanceof RoamDrone && !((RoamDrone) d).getIsTarget()) 
						hasOtherDrone = true;		
				}				
				
				if (hasOtherDrone) arena.addDrone(1);
				else {System.out.println("no drone to target");}
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			arena.drawArena(myCanvas);
			
		});
		
	}

	
	private void createAddCautiousDrone() {
		
		addCautiousDrone = new Button("Cautious");
		//addCautiousDrone.setStyle(buttonStyle);
		
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
		
		addDrone = new Button("Roamer");
		//addDrone.setStyle(buttonStyle);
		
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
		
		addWall = new Button("Wall");
		addWall.getStyleClass().add("button");
		
		//addWall.setStyle(buttonStyle);	
		Wall placementWall = new Wall(0, 0);
		createKeyEvents(placementWall);	
		createMouseEvents(placementWall);
		
		// Array needed as seperate int not allowed in mouse handler
		int[] mouse = new int[2];
		
		addWall.setOnAction(e -> {
			
			wallSelected = true;
			mouseClicked = false;
			
			
			wallAnimation = new AnimationTimer()
			{
				@Override
	            public void handle(long now)  {			
					
					if (mouseClicked && arena.getDroneAtWallPlacement(mouseX, mouseY, 
							placementWall.getWidth(), placementWall.getHeight()) != null) {
						
						System.out.println("1");
						
						arena.drawWallPlacement(myCanvas, mouseX, mouseY, 
								"red", placementWall);					
						
					} else if (mouseClicked && arena.getDroneAtWallPlacement(mouseX, mouseY, 
							placementWall.getWidth(), placementWall.getHeight()) == null) {						
						
					System.out.println("2");
					try {
						arena.addEnvironment(myCanvas, mouseX, mouseY, placementWall);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wallSelected = false;			
					wallAnimation.stop();
					
					} else {
						
						arena.drawWallPlacement(myCanvas, mouseX, mouseY, "grey_tran",
								placementWall);	
						
					}									
	             }	
			};	
			
			if (!animationPlaying)
				wallAnimation.start();				
		
	     });
	}
	
	public void createMouseEvents(Wall placementWall) {
				
		canvas.setOnMouseMoved(e -> {						
			mouseX = (int)e.getX();
			mouseY = (int)e.getY();
		});
													
		canvas.setOnMousePressed(e -> {
			
			mouseClicked = true;
			System.out.println("mouse Clicked");

		});	
		
		canvas.setOnMouseReleased(e -> {			
			
			mouseClicked = false;
			System.out.println("mouse released");
		});
		
		
		
	}
	
	public void createKeyEvents(Wall placementWall) {
		
		simPane.setOnKeyPressed(e -> {
			
			switch (e.getCode()) {
			case A : placementWall.rotateLeft();System.out.println("left"); break;
			case D : placementWall.rotateRight();System.out.println("right"); break;
			case W : placementWall.scaleUp();System.out.println("up"); break;
			case S : placementWall.scaleDown();System.out.println("down"); 
			}
			
		});
		
	}
	
	/**
	 * Create button to start animation
	 */
	private void createPlay() {
		
		play = new Button("Play");
		//play.setStyle(buttonHoverStyle);
		
		play.setOnAction(e -> {
			
			animationPlaying = true;
			//play.setStyle(buttonHoverStyle);
			
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
		//stop.setStyle(buttonStyle);
		
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
	
	


