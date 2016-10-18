package world.entities;

import java.awt.Graphics;

import main.HXImageLoader;
import world.HXClickable;
import world.HXEntity;
import world.HXWorld;

public class DataPin extends HXEntity implements HXClickable{

	private final int DEFAULT_WIDTH = 10, DEFAULT_HEIGHT = 43;
	private final int CLICK_RECT_W = 30, CLICK_RECT_H = 43;
	
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
	public DataPin(int xPos, int yPos, HXWorld w) {
		init(xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGHT, w);
		
		this.getRect().setBounds(
				xPos - DEFAULT_WIDTH - (CLICK_RECT_W - DEFAULT_WIDTH)/2, 
				yPos - DEFAULT_HEIGHT - (CLICK_RECT_H - DEFAULT_HEIGHT)/2, 
				CLICK_RECT_W, 
				CLICK_RECT_H);
	}
	
	/**
	 * Unless it has no visuals, it should override draw() but still call super.draw()
	 */
	@Override
	public void draw(Graphics g, float interpolation) {
		super.draw(g, interpolation);
		
		// map pins don't scale with zooms so they use getWidth() instead of getScaledWidth()
		g.drawImage( HXImageLoader.image_pin,
				getLastDraw_xPos() * getScale() + getxPan() - DEFAULT_WIDTH, 
				getLastDraw_yPos() * getScale() + getyPan() - DEFAULT_HEIGHT, 
				getWidth(), 
				getHeight(), 
				null
		);
	}

	@Override
	public void update() {
		super.update();
		
		this.getRect().setLocation(
				getLastDraw_xPos() * getScale() - DEFAULT_WIDTH - (CLICK_RECT_W - DEFAULT_WIDTH)/2, 
				getLastDraw_yPos() * getScale() - DEFAULT_HEIGHT - (CLICK_RECT_H - DEFAULT_HEIGHT)/2);
	}

	/**
	 * From the HXClickable interface. Add functionality for what happens when the object is clicked on in world
	 */
	@Override
	public void mouseIntersection() {
		this.remove();
		// TODO Auto-generated method stub
		
	}
}