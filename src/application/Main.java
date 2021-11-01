package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SimView simView = new SimView();
        Scene scene = new Scene(simView, 640, 480);
        stage.setTitle("Drone Simulator");
        stage.setScene(scene);
        stage.show();
        simView.draw();
    }

    public static void main(String[] args) {
        launch();
    }
}