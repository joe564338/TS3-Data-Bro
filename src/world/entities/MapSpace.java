package world.entities;

import java.awt.Color;
import java.awt.Graphics;

import world.HXEntity;
import world.HXWorld;

public class MapSpace extends HXEntity {

	private final int DEFAULT_WIDTH = 25, DEFAULT_HEIGHT = 25;
	
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
	public MapSpace(int xPos, int yPos, HXWorld w) {
		init(null, xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGHT, w);
	}
	
	/**
	 * Unless it has no visuals, it should override draw() but still call super.draw()
	 */
	@Override
	public void draw(Graphics g, float interpolation) {
		super.draw(g, interpolation);
		
		g.setColor(Color.gray);
		g.drawRect(
				getDraw_xPos(), 
				getDraw_yPos(), 
				getDraw_width(), 
				getDraw_height());
	}
}