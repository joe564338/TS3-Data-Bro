package world;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class HXEntity {
	
	private double xPos;
	private double yPos;
	private double xPos_prev;
	private double yPos_prev;
	private int    height;
	private int    width;
	
	private Rectangle rect;
	private HXWorld parentWorld;
	private Image img;

	private int draw_width;
	private int draw_height;
	private int draw_xPos;
	private int draw_yPos;
	private double draw_scale;
	
	/**
	 * The constructors of the classes in the 'entities' package should call this init() method.
	 * <p>
	 * Adds the newly instantiated entity to the parent HXWorld's array list of all entities and instatiates a new Rectangle object.
	 * @param x - x coordinate of spawn location.
	 * @param y - y coordinate of spawn location.
	 * @param w - width of entity.
	 * @param h - height of entity.
	 * @param m - mass of entity.
	 * @param parent - ST3World this entity will belong to.
	 */
	protected void init(Image i, double x, double y, int w, int h, HXWorld parent) {
		this.img = i;
		this.xPos = x;
		this.yPos = y;
		this.height = h;
		this.width = w;
		this.xPos_prev = x;
		this.yPos_prev = y;
		this.draw_width = (int) (parent.getScale() * w);
		this.draw_height = (int) (parent.getScale() * h);
		this.draw_scale = parent.getScale();
		this.rect = new Rectangle((int) xPos, (int) yPos, (int) width, (int) height); 
		this.parentWorld = parent;
		this.parentWorld.getEntities().add(0, this);
	}
	
	/**
	 * Called by ST3WorldPanel within its paintComponent() based on HXClock repaint timer.
	 * <p>
	 * Uses interpolation on constant timestep in HXClock to do smooth drawing as well as update the Rectangle object collider.
	 * @param g - The Graphics object context that will get painted on.
	 * @param interpolation - Sent by the HXClock to smooth movements when thread stutters or CPU lags.
	 */
	public void draw(Graphics g, float interpolation) { 
		draw_xPos = (int) (((xPos - xPos_prev) * interpolation + xPos_prev) * draw_scale);
		draw_yPos = (int) (((yPos - yPos_prev) * interpolation + yPos_prev) * draw_scale);
		
		// Draw image of this entity, does nothing if no img is set
		g.drawImage( img,
				draw_xPos, 
				draw_yPos, 
				draw_width, 
				draw_height, 
				null);
	}
	
	/**
	 * Called whenever an entity needs to be updated.
	 * <p>
	 * Used in conjunction with another class changing an entity's x or y positions.
	 */
	public void update() {
		
	}
	
	/**
	 * Used to delete an entity
	 * <p>
	 * Removes the caller from the world's entity array list.
	 */
	public void remove() {
		this.parentWorld.getEntities().remove(this);
	}
	
	// ============================   MARK: Getters and Setters ============================ 
	public double getxPos() {
		return xPos;
	}
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	public int getDraw_xPos() {
		return draw_xPos;
	}
	public void setDraw_xPos(int draw_xPos) {
		this.draw_xPos = draw_xPos;
	}
	public int getDraw_yPos() {
		return draw_yPos;
	}
	public void setDraw_yPos(int draw_yPos) {
		this.draw_yPos = draw_yPos;
	}
	public double getxPos_Prev() {
		return xPos_prev;
	}
	public void setxPos_Prev(double prevX) {
		this.xPos_prev = prevX;
	}
	public double getyPos_Prev() {
		return yPos_prev;
	}
	public void setyPos_Prev(double prevY) {
		this.yPos_prev = prevY;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Rectangle getRect() {
		return rect;
	}
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	public HXWorld getWorld() {
		return parentWorld;
	}
	public double getDraw_scale() {
		return draw_scale;
	}
	public void setDraw_scale(double draw_scale) {
		this.draw_scale = draw_scale;
		this.draw_width = (int) (this.width * draw_scale);
		this.draw_height = (int) (this.height * draw_scale);
	}
	public int getDraw_width() {
		return draw_width;
	}
	public void setDraw_width(int draw_width) {
		this.draw_width = draw_width;
	}
	public int getDraw_height() {
		return draw_height;
	}
	public void setDraw_height(int draw_height) {
		this.draw_height = draw_height;
	}	
	public Image getImg() {
		return this.img;
	}
}