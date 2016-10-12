package world.entities;

import java.awt.Color;
import java.awt.Graphics;

import world.HXClickable;
import world.HXEntity;
import world.HXWorld;

public class HXEntityTemplate extends HXEntity implements HXClickable{

	private final int DEFAULT_WIDTH = 50, DEFAULT_HEIGHT = 50;
	
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
	public HXEntityTemplate(int xPos, int yPos, double xVel, double yVel, HXWorld w) {
		init(xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGHT, xVel, yVel, 1, 3, w);
	}
	
	/**
	 * Unless it has no visuals, it should override draw() but still call super.draw()
	 */
	@Override
	public void draw(Graphics g, float interpolation) {
		super.draw(g, interpolation);
		
		g.setColor(Color.gray);
		g.drawRect(getLastDraw_xPos(), getLastDraw_yPos(), (int) getWidth(), (int) getHeight());
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