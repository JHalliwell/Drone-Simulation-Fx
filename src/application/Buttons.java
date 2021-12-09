package application;

import java.io.FileNotFoundException;
import javafx.animation.AnimationTimer;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Holds information and functions of all buttons and their logic
 * @author 29020945
 */
public class Buttons extends HBox {

	private Button addDrone, addAttackDrone, addCautiousDrone, play, 
				stop, addWall, addBlackHole;
	
	private AnimationTimer mainAnimation, wallAnimation, holeAnimation;
	private DroneArena arena;
	private Canvas canvas;
	private MyCanvas myCanvas;
	private BorderPane simPane;
	private SoundEffects soundEffects;
	private boolean wallSelected, holeSelected, animationPlaying, mouseClicked;
	private int mouseX, mouseY;
	
	/**
	 * @param arena - Main arena of drone's and environment
	 * @param myCanvas - Methods to draw arena 
	 * @param canvas - On which the arena is drawn
	 * @param simPane - BorderPane of different nodes
	 * @throws FileNotFoundException
	 */
	public Buttons(DroneArena arena, MyCanvas myCanvas, Canvas canvas, 
						BorderPane simPane) throws FileNotFoundException {
		
		this.simPane = simPane;
		this.arena = arena;
		this.myCanvas = myCanvas;
		this.canvas = canvas;
		
		soundEffects = new SoundEffects();
		createPlay();
		createStop();
		createAddDrone();
		createAddAttackDrone();
		createAddCautiousDrone();
		createAddWall();
		createAddBlackHole();
		addButtons();
		setButtonLayout();	
			
	} 
	
	/**
	 * Create button to start animation
	 */
	private void createPlay() {
		
		play = new Button("Play");
		
		play.setOnAction(e -> {
			
			if (!animationPlaying) {
				soundEffects.playAnimationMusic();
				animationPlaying = true;
				play.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);	// Change button style
			}			
			
			// De-select wall/blackhole if animation has started
			wallSelected = false;
			holeSelected = false;
			addBlackHole.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false); // Change button style
			addWall.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false); // Change button Style
			
			if (mainAnimation != null) {
				mainAnimation.stop();
			}
			
			mainAnimation = new AnimationTimer()
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
	        mainAnimation.start();
			
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
				soundEffects.stopAnimationMusic();
				mainAnimation.stop();
			}			
			
					
			
		});
		
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
				
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("Cannot spawn Honer Ship");
					alert.setContentText("There is no roamer to target");
					alert.showAndWait();
				}
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			arena.drawArena(myCanvas);
			
		});
		
	}
	
	/**
	 * Create's 'Cautious' button, adds cautious drone to arena when pressed
	 */
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
	 * Create button to add roamer drone to arena
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
	
	/**
	 * Creates the addWall button and handles logic of wall being moved,
	 * 	scaled, rotated and placed
	 * @throws FileNotFoundException
	 */
	private void createAddWall() throws FileNotFoundException {
		
		addWall = new Button("Wall");
	
		Wall placementWall = new Wall(0, 0);
		createKeyEvents(placementWall);	
		createMouseEvents();
		
		addWall.setOnAction(e -> {					
			
			wallAnimation = new AnimationTimer()
			{
				@Override
	            public void handle(long now)  {	
					
					if (mouseClicked && (arena.getDroneAtEnvironmentPlacement(mouseX, mouseY, 
							placementWall.getWidth(), placementWall.getHeight()) != null || 
							arena.getEnvironmentAtPlacement(mouseX, mouseY, placementWall.getWidth(), 
									placementWall.getHeight()) != null) && wallSelected) {
						
						soundEffects.playError();								
						arena.drawEnvironmentPlacement(myCanvas, mouseX, mouseY, 
								"red", placementWall);	
						
					} else if (mouseClicked && wallSelected) {			
						
					try {
						
						System.out.println("placing wall");
						soundEffects.playClick();
						addWall.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
						arena.addEnvironmentPlacement(myCanvas, mouseX, mouseY, placementWall);
						wallSelected = false;			
						this.stop();
						
					} 
					catch (FileNotFoundException e) {
						e.printStackTrace();
					}		
					
					} else if (wallSelected) {						
						arena.drawEnvironmentPlacement(myCanvas, mouseX, mouseY, "grey_tran",
								placementWall);							
					}						
	             }	
			};			
			
			// If animation isn't playing and wall isn't already selected, start animation
			// 	and change button style. If wall is already selected, turn off selected
			if (!animationPlaying && !wallSelected && !holeSelected) {
				wallAnimation.start();					
				wallSelected = true;
				addWall.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
				mouseClicked = false;
			} else if (wallSelected) {
				wallSelected = false;
				addWall.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
				wallAnimation.stop();
			}
		
	     });
	}
	
	/**
	 * Creates Black Hole button and handles logic of it being moved
	 * 	and placed
	 * @throws FileNotFoundException
	 */
	public void createAddBlackHole() throws FileNotFoundException {
		
		addBlackHole = new Button("Black Hole");
		createMouseEvents();
		BlackHole blackHole = new BlackHole(0, 0);
		
		addBlackHole.setOnAction(e -> {
			
			holeAnimation = new AnimationTimer() {
				
				public void handle(long now) {
					
					if (mouseClicked && (arena.getDroneAtEnvironmentPlacement(mouseX, mouseY, 
							blackHole.getFieldWidth(), blackHole.getFieldHeight()) != null ||
							arena.getEnvironmentAtPlacement(mouseX, mouseY, blackHole.getFieldWidth(), 
							blackHole.getFieldHeight()) != null) && holeSelected) {
						
						arena.drawEnvironmentPlacement(myCanvas, mouseX, mouseY, "red", blackHole);
						soundEffects.playError();
						
					} else if (mouseClicked && holeSelected) {						
						
						System.out.println("Add hole");
						
						try {
							soundEffects.playClick();
							addBlackHole.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
							arena.addEnvironmentPlacement(myCanvas, mouseX, mouseY, blackHole);
							holeSelected = false;
							this.stop();
						} catch (FileNotFoundException e) {							
							e.printStackTrace();
						}
												
					} else if (holeSelected) {			
						
						arena.drawEnvironmentPlacement(myCanvas, mouseX, mouseY, 
														"hole",	blackHole);	
	
					}					
				}				
			};		
			
			// If animation isn't playing and hole isn't already selected, start animation
			// 	and change button style. If hole is already selected, turn off selected
			if (!animationPlaying && !wallSelected && !holeSelected) {
				holeAnimation.start();					
				holeSelected = true;
				addBlackHole.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
				mouseClicked = false;
			} else if (holeSelected) {
				holeSelected = false;
				addBlackHole.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
				holeAnimation.stop();
			}
			
		});		
	}
		
	/**
	 * Add buttons to the HBox
	 */
	private void addButtons() {
		
		Text animation, drones, environment;

		animation = new Text("Animation");
		drones = new Text("Ships");
		environment = new Text("Environment");
		
		Separator one, two;
		one = new Separator(Orientation.VERTICAL);
		two = new Separator(Orientation.VERTICAL);
		
		this.getChildren().addAll(animation, play, stop, one, drones, addDrone, addCautiousDrone, 
									addAttackDrone, two, environment, addWall, addBlackHole);
		
	}
	
	/**
	 * Set the layout of the buttons
	 */
	private void setButtonLayout() {
		
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(10);
		
	}
	
	/**
	 * Creates the mouse event handler for mouse movement and clicking
	 */
	public void createMouseEvents() {				
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
	
	/**
	 * Creates key events handler for placementWall scale and rotate,
	 * 	and to cancel selection
	 * @param placementWall
	 */
	public void createKeyEvents(Wall placementWall) {
		
		simPane.setOnKeyPressed(e -> {
			
			switch (e.getCode()) {
			case A : placementWall.rotate();break;
			case D : placementWall.rotate();break;
			case W : placementWall.scaleUp();break;
			case S : placementWall.scaleDown();break;
			case ESCAPE : 
				wallSelected = false; 
				holeSelected = false; 
				addBlackHole.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
				addWall.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
			}
			
		});
		
	}	
}
	
	


