package application;

import java.io.File;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView;  


public class SoundEffects {

	Media backgroundMusic, explosion, click, error;
	MediaPlayer player;	
	
	SoundEffects() {

		
		backgroundMusic = new Media(new File("sounds/backgroundMusic.mp3").toURI().toString());
		click = new Media(new File("sounds/click.mp3").toURI().toString());
		error = new Media(new File("sounds/error.mp3").toURI().toString());
		explosion = new Media(new File("sounds/explosion.mp3").toURI().toString());
		
	}
	
	public void playBackgroundMusic() {
		
		player = new MediaPlayer(backgroundMusic);
		player.setVolume(0.6);
		player.play();		
		
	}
	
	public void playClick() {
		
		player = new MediaPlayer(click);
		player.play();
		
	}
	
	public void playExplosion() {
		
		player = new MediaPlayer(explosion);
		player.play();
		
	}
	
	public void playError() {
		
		player = new MediaPlayer(error);
		player.play();
		
	}
	
}
