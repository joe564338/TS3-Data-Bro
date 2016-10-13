package world.entities;

import java.awt.Color;
import java.awt.Graphics;

import main.HXImageLoader;
import world.HXClickable;
import world.HXEntity;
import world.HXWorld;

public class MapBackground extends HXEntity implements HXClickable{
	
	/**
	 * Creates the background image for the world
	 * @param xPos
	 * @param yPos
	 * @param xVel
	 * @param yVel
	 * @param w
	 */
	public MapBackground(int xPos, int yPos, int xClip, int yClip, double xVel, double yVel, HXWorld w) {
//		init(xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGHT, xVel, yVel, 1, 3, w);
		
	}
	
	/**
	 * Unless it has no visuals, it should override draw() but still call super.draw()
	 */
	@Override
	public void draw(Graphics g, float interpolation) {
		super.draw(g, interpolation);

		
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