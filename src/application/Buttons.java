package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import javafx.animation.AnimationTimer;
import javafx.css.PseudoClass;
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
	SoundEffects soundEffects;
	private boolean wallSelected, animationPlaying, 
						placeWall, mouseReleased, mouseClicked;
	private int mouseX, mouseY;
	
	public Buttons(DroneArena arena, MyCanvas myCanvas, Canvas canvas, 
						BorderPane simPane) throws FileNotFoundException {
		
		this.simPane = simPane;
		this.arena = arena;
		this.myCanvas = myCanvas;
		this.canvas = canvas;
		soundEffects = new SoundEffects();
		createAddDrone();
		createAddAttackDrone();
		createAddCautiousDrone();
		createPlay();
		createStop();
		createAddWall();
		animation();
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

	private void createAddWall() throws FileNotFoundException {
		
		addWall = new Button("Wall");
		
		//addWall.setStyle(buttonStyle);	
		Wall placementWall = new Wall(0, 0);
		createKeyEvents(placementWall);	
		createMouseEvents(placementWall);
		
		addWall.setOnAction(e -> {
			
			wallSelected = true;
			addWall.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
			mouseClicked = false;
			
			
			wallAnimation = new AnimationTimer()
			{
				@Override
	            public void handle(long now)  {			
					
					if (mouseClicked && arena.getDroneAtWallPlacement(mouseX, mouseY, 
							placementWall.getWidth(), placementWall.getHeight()) != null) {

						soundEffects.playError();
						arena.drawWallPlacement(myCanvas, mouseX, mouseY, 
								"red", placementWall);					
						
					} else if (mouseClicked && arena.getDroneAtWallPlacement(mouseX, mouseY, 
							placementWall.getWidth(), placementWall.getHeight()) == null) {						

					try {
						soundEffects.playClick();
						addWall.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
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


		});	
		
		canvas.setOnMouseReleased(e -> {			
			
			mouseClicked = false;

		});
		
		
		
	}
	
	public void createKeyEvents(Wall placementWall) {
		
		simPane.setOnKeyPressed(e -> {
			
			switch (e.getCode()) {
			case A : placementWall.rotate();break;
			case D : placementWall.rotate();break;
			case W : placementWall.scaleUp();break;
			case S : placementWall.scaleDown();break;
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
			play.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
			
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
			
			if (animationPlaying = true) {
				animationPlaying = false;
				play.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
				animationTimer.stop();
			}			
			
					
			
		});
		
	}
	
	private void animation() {
		
		Button a = new Button("explosion");
		
		a.setOnAction(e -> {
			Explosion explosion;
			try {
				explosion = new Explosion();
				explosion.showExplosion(100, 100, myCanvas);
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		});
		
		this.getChildren().add(a);
		
	}
	
	/**
	 * Set the layout of the buttons
	 */
	private void setButtonLayout() {
		
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(10);
		
	}
}
	
	


