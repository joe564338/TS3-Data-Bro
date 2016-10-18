package world.entities;

import java.awt.Graphics;

import main.HXImageLoader;
import world.HXEntity;
import world.HXWorld;

public class BackgroundMap extends HXEntity {

	private final int DEFAULT_WIDTH = 1425, DEFAULT_HEIGHT = 742;
	
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
	public BackgroundMap(HXWorld w) {
		init(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, w);
	}
	
	/**
	 * Unless it has no visuals, it should override draw() but still call super.draw()
	 */
	@Override
	public void draw(Graphics g, float interpolation) {
		super.draw(g, interpolation);
		
		g.drawImage( HXImageLoader.image_map,
				getLastDraw_xPos() * getScale() + getxPan(), 
				getLastDraw_yPos() * getScale() + getyPan(), 
				getScaledWidth(), 
				getScaledHeight(), 
				null
		);
	}

	@Override
	public void update() {
		super.update();
	}

}
