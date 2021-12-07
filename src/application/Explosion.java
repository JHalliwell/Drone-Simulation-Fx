package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Explosion {

		ArrayList<String> frames;
		ArrayList<Image> frameImages;
		int size = 40;
		
		Explosion() throws IOException {

			File directory = new File("graphics/explosion");
			frames = new ArrayList<String>();
			frameImages = new ArrayList<Image>();
			frames = getAllImages(directory, false);
			//System.out.println(frames.size());
			
			for (int i = 0; i < frames.size(); i++) {
				
				//System.out.println(frames.get(i));
				frameImages.add(new Image(new FileInputStream(frames.get(i))));
				
			}
			
		}
		
		public void showExplosion(int xPos, int yPos, MyCanvas canvas) throws InterruptedException {
					
//			canvas.drawImage(frameImages.get(5), xPos , yPos, size, size);
//			canvas.drawImage(frameImages.get(10), xPos+10, yPos+10, size, size);
//			canvas.drawImage(frameImages.get(15), xPos+20, yPos+20, size, size);
			
			
			for (int i = 0; i < frameImages.size(); i++) {
				
				//canvas.clearArea(size, xPos, yPos);
				canvas.drawImage(frameImages.get(i), xPos, yPos, size, size);
			 			
			}
			
		}
		
		private void setImages() throws FileNotFoundException {
			Image frame0 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame1 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame2 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame3 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame4 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame5 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame6 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame7 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame8 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame9 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame10 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame11 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame12 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame13 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame14 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame15 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame16 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame17 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame18 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame19 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame20 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame21 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame22 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame23 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame24 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame25 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame26 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame27 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame28 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame29 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame30 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame31 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame32 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame33 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame34 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame35 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame36 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame37 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame38 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame39 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame40 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame41 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame42 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame43 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame44 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame45 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame46 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame47 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame48 = new Image(new FileInputStream("graphics/explosion/tile000"));
			Image frame49 = new Image(new FileInputStream("graphics/explosion/tile000"));
		}
		
		/**
		 * (http://www.java2s.com/Code/Java/2D-Graphics-GUI/Returnsalljpgimagesfromadirectoryinanarray.htm)
		 * Adds all image string locations with .png to an arrayList
		 * @param directory	- Directory to add files from
		 * @param descendIntoSubDirectories	- boolean to check subdirectories
		 * @return
		 * @throws IOException
		 */
	    public static ArrayList<String> getAllImages(File directory, boolean descendIntoSubDirectories) throws IOException {
	        ArrayList<String> resultList = new ArrayList<String>(256);
	        File[] f = directory.listFiles();
	        
//	        for (int i = 0; i < f.length; i++) {
//	        	System.out.println(f[i]);
//	        }
	        
	        for (File file : f) {
	            if (file != null && file.getName().toLowerCase().endsWith(".png") && !file.getName().startsWith("tn_")) {
	            	String image = file.getCanonicalPath();	
	            	//resultList.add(image.substring(image.length() - 27));
	            	resultList.add(image);
	            }
	            if (descendIntoSubDirectories && file.isDirectory()) {
	                ArrayList<String> tmp = getAllImages(file, true);
	                if (tmp != null) {
	                    resultList.addAll(tmp);
	                }
	            }
	        }
	        if (resultList.size() > 0)
	            return resultList;
	        else
	            return null;
	    }

	
}
