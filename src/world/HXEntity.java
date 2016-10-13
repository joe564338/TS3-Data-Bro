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
	private double height;
	private double width;
	private double scaledHeight;
	private double scaledWidth;
	private Rectangle rect;
	private double mass;
	private HXWorld parentWorld;
	private double lifespan; // an entity with -1 lifespan will not decay
	private double xVel;
	private double yVel;
	private double scale;
	
	private double LIFESPAN_DECAY = 0;// 0.04;
	private double FRICTION = 0;//0.05;
	
	
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
	protected void init(double x, double y, double w, double h, double velX, double velY, double m, double life, HXWorld parent) {
		this.xPos = x;
		this.yPos = y;
		this.height = h;
		this.width = w;
		this.scaledHeight = h * parent.getParentPanel().getZoom();
		this.scaledWidth = w * parent.getParentPanel().getZoom();
		this.mass = m;
		this.xPos_prev = x;
		this.yPos_prev = y;
		this.lifespan = life;
		this.rect = new Rectangle((int) xPos, (int) yPos, (int) width, (int) height); 
		this.parentWorld = parent;
		this.parentWorld.getEntities().add(0, this);
		this.xVel = velX;
		this.yVel = velY;
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
		rect.setLocation((int) xPos, (int) yPos); 
	}
	
	/**
	 * Called whenever an entity needs to be updated.
	 * <p>
	 * Used in conjunction with another class changing an entity's x or y positions.
	 */
	public void update() {
		setxPos_Prev(getxPos());
		setyPos_Prev(getyPos());
		
		xPos += xVel;
		yPos += yVel;
		
		xVel = Math.max( xVel - FRICTION, 0);
		yVel = Math.max( yVel - FRICTION, 0);
		
		if (lifespan != -1) {
			if (lifespan <= 0) {
				remove();
			} else {
				lifespan -= LIFESPAN_DECAY;
			}
		}
		
		// TODO Change xPos or yPos variables for movement
	}
	
	public void rescaleSize(double zoom) {
		scaledHeight = height * zoom;
		scaledWidth = width * zoom;
		scale = zoom;
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
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public Rectangle getRect() {
		return rect;
	}
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	public Double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public HXWorld getWorld() {
		return parentWorld;
	}
	public Double getLifespan() {
		return lifespan;
	}
	public void setLifespan(double lifespan) {
		this.lifespan = lifespan;
	}
	public double getxVel() {
		return xVel;
	}
	public void setxVel(double xVel) {
		this.xVel = xVel;
	}
	public double getyVel() {
		return yVel;
	}
	public void setyVel(double yVel) {
		this.yVel = yVel;
	}
	public double getScaledHeight() {
		return scaledHeight;
	}
	public void setScaledHeight(double scaledHeight) {
		this.scaledHeight = scaledHeight;
	}
	public double getScaledWidth() {
		return scaledWidth;
	}
	public void setScaledWidth(double scaledWidth) {
		this.scaledWidth = scaledWidth;
	}
	public double getScale() {
		return this.scale;
	}
}