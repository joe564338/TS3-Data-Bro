package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HXImageLoader {
	
	public static BufferedImage mapImage;
	
	public HXImageLoader() {
		 try 
		    {                
			 mapImage = ImageIO.read(HXImageLoader.class.getResourceAsStream("/resources/map.png")); 
		    } 
		    catch (IOException e) 
		    { 
		      //Not handled. 
		    } 
	}

}
