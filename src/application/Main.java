package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main Class in which the application starts
 * @author 29020945
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
    	
        SimView simView = new SimView();	  
        stage = simView.getSimStage(); // Set the stage that has been created in SimView          
        stage.show();
        
    }
    
}