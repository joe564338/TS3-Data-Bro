package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import main.engine.HXClock;
import main.engine.HXClockRenderer;
import world.HXEntity;
import world.HXWorld;

public class HXWorldPanel extends JPanel implements HXClockRenderer, MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	/* === Updates and drawing === */
	private HXClock clock;
	private float interpolation = 0;
	
	/* === 2D render canvas === */
	private Image environment;
	private Graphics graphics;
	
	/* === Mouse Control === */
	private Boolean mousePressed = false;
	
	/* === world === */
	private HXWorld world;
	
	private double zoom = 0.5;
	
	/**
	 * Extension of a JPanel that contains an HXWorld and paints all of this object's entities.
	 */
	public HXWorldPanel(int worldStartX, int worldStartY) {
		setLayout(null);
		setBounds(-worldStartX, -worldStartY, (int) (HXWorld.WORLD_WIDTH * zoom), (int) (HXWorld.WORLD_HEIGHT * zoom));
		addMouseListener(this);
		addMouseMotionListener(this);
		
		
		world = new HXWorld(this);
		clock = new HXClock(this);
	}
	
	public void paint(Graphics g) {
		// - Render setup
		environment = createImage(getWidth(), getHeight());
		graphics = environment.getGraphics();
		paintComponent(graphics);
		g.drawImage(environment, 0, 0, null);
	}
	
	/**
	 * Internal usage only.
	 * <p>
	 * Only called by the paint(Graphics g) method.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// - Panel background
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// - Entity's added to graphics
		for (HXEntity e: world.getEntities())
			e.draw(g, interpolation);
		
		// - Panel border
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);	
	}
	
	public void newWorld() {
		world = new HXWorld(this);
	}
	
	public void setInterpolation(float interpolation) {
		this.interpolation = interpolation;
	}
	
	public HXWorld getWorld() {
		return world;
	}
	public HXClock getClock() {
		return clock;
	}
	public Boolean getMousePressed() {
		return mousePressed;
	}

	@Override
	public void repaintWorld(float withInterpolation) {
		setInterpolation(withInterpolation);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		world.mousePress(e.getX(), e.getY());
		mousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		world.mousePress(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
		world.updateZoom(zoom);
		this.setSize(new Dimension((int) (HXWorld.WORLD_WIDTH * zoom), (int) (HXWorld.WORLD_HEIGHT * zoom)));
	}
	public void incrementZoom() {
		if (zoom < 5) {
			zoom += 0.25;
			world.updateZoom(zoom);
			this.setSize(new Dimension((int) (HXWorld.WORLD_WIDTH * zoom), (int) (HXWorld.WORLD_HEIGHT * zoom)));
		}
	}
	public void decrementZoom() {
		if (zoom > 0.5) {
			zoom -= 0.25;
			world.updateZoom(zoom);
			this.setSize(new Dimension((int) (HXWorld.WORLD_WIDTH * zoom), (int) (HXWorld.WORLD_HEIGHT * zoom)));
		}
	}
}
