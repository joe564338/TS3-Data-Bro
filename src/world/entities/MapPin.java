package world.entities;

import java.awt.Graphics;

import main.HXImageLoader;
import world.HXClickable;
import world.HXEntity;
import world.HXWorld;

public class MapPin extends HXEntity implements HXClickable{

	private final int DEFAULT_WIDTH = 10, DEFAULT_HEIGHT = 40;
	private final int CLICK_RECT_W = 30, CLICK_RECT_H = 40;
	
	/**
	 * Template for creating a new entiity. Should always override drawing, to have
	 * some sort of visual appearence in the world. But should still call super.draw()
	 * Constructor should always call init() method of HXEntity
	 * @param xPos
	 * @param yPos
	 * @param xVel
	 * @param yVel
	 * @param w
	 */
	public MapPin(int xPos, int yPos, HXWorld w) {
		init(HXImageLoader.image_pin, xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGHT, w);
		
		this.getRect().setBounds(
				xPos - DEFAULT_WIDTH/2 - (CLICK_RECT_W - DEFAULT_WIDTH)/2, 
				yPos - DEFAULT_HEIGHT - (CLICK_RECT_H - DEFAULT_HEIGHT)/2, 
				CLICK_RECT_W, 
				CLICK_RECT_H);
	}

	@Override
	public void update() {
		super.update();
		
		this.getRect().setLocation(
				(int) (getDraw_xPos() * getDraw_scale() - DEFAULT_WIDTH/2 - (CLICK_RECT_W - DEFAULT_WIDTH)/2), 
				(int) (getDraw_yPos() * getDraw_scale() - DEFAULT_HEIGHT - (CLICK_RECT_H - DEFAULT_HEIGHT)/2));
	}

	@Override
	public void draw(Graphics g, float interpolation) { 
		this.setDraw_xPos((int) ((this.getxPos() - this.getxPos_Prev()) * interpolation + this.getxPos_Prev()));
		this.setDraw_yPos((int) ((this.getyPos() - this.getyPos_Prev()) * interpolation + this.getyPos_Prev()));
		
		// Draw image of this entity, does nothing if no img is set
		g.drawImage( this.getImg(),
				(int) (this.getDraw_xPos() * this.getDraw_scale()) - DEFAULT_WIDTH/2, 
				(int) (this.getDraw_yPos() * this.getDraw_scale()) - DEFAULT_HEIGHT, 
				DEFAULT_WIDTH, 
				DEFAULT_HEIGHT, 
				null);
	}
	
	/**
	 * From the Clickable interface. Add functionality for what happens when the object is clicked on in world
	 */
	@Override
	public void mouseIntersection() {
		this.remove();
		System.out.println("Hey");
		// TODO Auto-generated method stub
		
	}
}