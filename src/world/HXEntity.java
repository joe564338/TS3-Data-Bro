package world;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class HXEntity {
	
	private double xPos_prev;
	private double yPos_prev;
	private int lastDraw_xPos;
	private int lastDraw_yPos;
	private double xPos;
	private double yPos;
	private int height;
	private int width;
	private int scaledHeight;
	private int scaledWidth;
	private Rectangle rect;
	private HXWorld parentWorld;
	private int xPan;
	private int yPan;
	private int scale;
	
	/**
	 * The constructors of the classes in the 'entities' package should call this init() method.
	 * <p>
	 * Adds the newly instantiated entity to the parent HXWorld's array list of all entities and instatiates a new Rectangle object.
	 * @param x - x coordinate of spawn location.
	 * @param y - y coordinate of spawn location.
	 * @param w - width of entity.
	 * @param h - height of entity.
	 * @param m - mass of entity.
	 * @param parent - HXWorld this entity will belong to.
	 */
	protected void init(double x, double y, int w, int h, HXWorld parent) {
		this.xPos = x;
		this.yPos = y;
		this.xPan = parent.getParentPanel().getCameraPosX();
		this.yPan = parent.getParentPanel().getCameraPosY();
		this.height = h;
		this.width = w;
		this.scaledHeight = (int) (h * parent.getParentPanel().getZoom());
		this.scaledWidth = (int) (w * parent.getParentPanel().getZoom());
		this.xPos_prev = x;
		this.yPos_prev = y;
		this.rect = new Rectangle((int) xPos, (int) yPos, (int) width, (int) height); 
		this.parentWorld = parent;
		this.parentWorld.getEntities().add(0, this);
		this.scale = parent.getParentPanel().getZoom();
	}
	
	/**
	 * Called by HXWorldPanel within its paintComponent() based on HXClock repaint timer.
	 * <p>
	 * Uses interpolation on constant timestep in HXClock to do smooth drawing as well as update the Rectangle object collider.
	 * @param g - The Graphics object context that will get painted on.
	 * @param interpolation - Sent by the HXClock to smooth movements when thread stutters or CPU lags.
	 */
	public void draw(Graphics g, float interpolation) { 
		int drawX = (int) ((getxPos() - getxPos_Prev()) * interpolation + getxPos_Prev());
		int drawY = (int) ((getyPos() - getyPos_Prev()) * interpolation + getyPos_Prev());
		// Actual image that gets drawn is handled by each entity separately
		setLastDraw_xPos(drawX);
		setLastDraw_yPos(drawY);
		// draw() updates Rectangle location with where it visually is rendered
//		rect.setLocation((int) xPos, (int) yPos); 
	}
	
	/**
	 * Called whenever an entity needs to be updated.
	 * <p>
	 * Used in conjunction with another class changing an entity's x or y positions.
	 */
	public void update() {
		
	}
	
	public void rescaleSize(int zoom) {
		scaledHeight = height * zoom;
		scaledWidth = width * zoom;
		scale = zoom;
	}
	
	public void panShift(int xS, int yS) {
		this.xPan = xS;
		this.yPan = yS;
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
	public int getLastDraw_xPos() {
		return lastDraw_xPos;
	}
	public void setLastDraw_xPos(int prevX) {
		this.lastDraw_xPos = prevX;
	}
	public int getLastDraw_yPos() {
		return lastDraw_yPos;
	}
	public void setLastDraw_yPos(int prevY) {
		this.lastDraw_yPos = prevY;
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
	public int getScaledHeight() {
		return scaledHeight;
	}
	public void setScaledHeight(int scaledHeight) {
		this.scaledHeight = scaledHeight;
	}
	public int getScaledWidth() {
		return scaledWidth;
	}
	public void setScaledWidth(int scaledWidth) {
		this.scaledWidth = scaledWidth;
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
	public int getScale() {
		return this.scale;
	}
	public int getxPan() {
		return xPan;
	}
	public int getyPan() {
		return yPan;
	}
	
}