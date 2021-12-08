module DroneFxNew {
	
	requires javafx.controls;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.media;
	
	opens application to javafx.graphics, javafx.fxml;
	
}
