package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HXImageLoader {
	
	public static BufferedImage image_map;
	public static BufferedImage image_pin;
	public static BufferedImage image_zoom_in;
	public static BufferedImage image_zoom_out;
	
	public HXImageLoader() {
		 try 
		    {                
			 image_map = ImageIO.read(HXImageLoader.class.getResourceAsStream("/resources/map.png")); 
			 image_pin = ImageIO.read(HXImageLoader.class.getResourceAsStream("/resources/pin.png"));
			 image_zoom_in = ImageIO.read(HXImageLoader.class.getResourceAsStream("/resources/zoomIcon_inc@2x.png"));
			 image_zoom_out = ImageIO.read(HXImageLoader.class.getResourceAsStream("/resources/zoomIcon_dec@2x.png"));
		    } 
		    catch (IOException e) 
		    { 
		      //Not handled. 
		    } 
	}
}
