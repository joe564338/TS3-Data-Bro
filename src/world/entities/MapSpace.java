package world.entities;

import java.awt.Color;
import java.awt.Graphics;

import main.HXImageLoader;
import world.HXClickable;
import world.HXEntity;
import world.HXWorld;

public class MapSpace extends HXEntity implements HXClickable{

	private final int DEFAULT_WIDTH = 25, DEFAULT_HEIGHT = 14;
	
	private int xImgClip = 0;
	private int yImgClip = 0;
	
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
	public MapSpace(int xPos, int yPos, int xClip, int yClip, double xVel, double yVel, HXWorld w) {
		init(xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGHT, xVel, yVel, 1, 3, w);
		
		this.xImgClip = xClip;
		this.yImgClip = yClip;
	}
	
	/**
	 * Unless it has no visuals, it should override draw() but still call super.draw()
	 */
	@Override
	public void draw(Graphics g, float interpolation) {
		super.draw(g, interpolation);

		g.drawImage(HXImageLoader.image_HXTemplate,
				(int) (getLastDraw_xPos() * getScale()), 
				(int) (getLastDraw_yPos() * getScale()), 
				(int) (getLastDraw_xPos() * getScale() + getScaledWidth()), 
				(int) (getLastDraw_yPos() * getScale() + getScaledHeight()), 
				xImgClip*DEFAULT_WIDTH, 
				yImgClip*DEFAULT_HEIGHT, 
				xImgClip*DEFAULT_WIDTH + DEFAULT_WIDTH, 
				yImgClip*DEFAULT_HEIGHT + DEFAULT_HEIGHT, null);
		
		g.setColor(Color.gray);
		g.drawRect((int) (getLastDraw_xPos() * getScale()), (int) (getLastDraw_yPos() * getScale()), (int) getScaledWidth(), (int) getScaledHeight());
	}

	@Override
	public void update() {
		super.update();
	}

	/**
	 * From the HXClickable interface. Add functionality for what happens when the object is clicked on in world
	 */
	@Override
	public void mouseIntersection() {
		// TODO Auto-generated method stub
		
	}
}