package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class SoundEffects {

	Media animationMusic, click, error, blackhole, explosion;
	MediaPlayer player, animationPlayer, errorPlayer;	
	
	SoundEffects() {
		
		animationMusic = new Media(new File("sounds/ambientSpace.mp3").toURI().toString());
		click = new Media(new File("sounds/click.mp3").toURI().toString());
		error = new Media(new File("sounds/error.mp3").toURI().toString());
		blackhole = new Media(new File("sounds/blackhole.mp3").toURI().toString());
		explosion = new Media(new File("sounds/explosion.mp3").toURI().toString());
		
		errorPlayer = new MediaPlayer(error);
		errorPlayer.setVolume(0.7);		
		
	}
	
	public void playAnimationMusic() {
		
		animationPlayer = new MediaPlayer(animationMusic);
		animationPlayer.setVolume(0.4);
		// Replay sound if it ends
		animationPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				animationPlayer.seek(Duration.ZERO);
			}
		});
		animationPlayer.play();		
		
	}
	
	public void stopAnimationMusic() {
		
		if (animationPlayer != null) {
			animationPlayer.stop();
		}
		
	}
	
	public void playClick() {
		
		player = new MediaPlayer(click);
		player.play();
		
	}
	
	public void playBlackHole() {
		System.out.println("play black hole");
		player = new MediaPlayer(blackhole);
		player.play();
	}
	
	public void playExplosion() {
		player = new MediaPlayer(explosion);
		player.play();
	}
	
	public void playError() {		
		
		errorPlayer.stop();
		errorPlayer.play();
		
	}
	
}
