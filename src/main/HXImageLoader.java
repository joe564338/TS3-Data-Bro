package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HXImageLoader {
	
	public static BufferedImage image_map;
	public static BufferedImage image_pin;
	
	public HXImageLoader() {
		 try 
		    {                
			 image_map = ImageIO.read(HXImageLoader.class.getResourceAsStream("/resources/map.png")); 
			 image_pin = ImageIO.read(HXImageLoader.class.getResourceAsStream("/resources/pin.png")); 
		    } 
		    catch (IOException e) 
		    { 
		      //Not handled. 
		    } 
	}

}
