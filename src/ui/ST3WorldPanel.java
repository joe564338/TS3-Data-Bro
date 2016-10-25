package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import main.HXStartup;
import main.engine.HXClock;
import main.engine.HXClockRenderer;
import world.HXEntity;
import world.HXWorld;
import world.entities.MapPin;

public class ST3WorldPanel extends JPanel implements HXClockRenderer, MouseListener, MouseMotionListener, MouseWheelListener{
	private static final long serialVersionUID = 1L;
	
	/* === Updates and drawing === */
	private HXClock clock;
	private float interpolation = 0;
	
	/* === 2D render canvas === */
	private Image environment;
	private Graphics graphics;
	
	/* === Mouse Control === */
//	private Boolean mousePressed = false;
	
	/* === world === */
	private ST3Camera camera;
	private double mouse_x_last = 0;
	private double mouse_y_last = 0;
	
	/* === world === */
	private HXWorld world;

	/**
	 * Extension of a JPanel that contains an ST3World and paints all of this object's entities.
	 */
	public ST3WorldPanel(int width, int height, int worldStartX, int worldStartY) {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, width, height);
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		// Zoom buttons ...
//		JButton button = new JButton();
//		button.setIcon(new ImageIcon(HXImageLoader.image_zoom_out));
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				camera.decrementZoom();
//			}
//		});
//		button.setBounds(100, 100, 60, 60);
//		this.add(button);
//		
//		JButton button_1 = new JButton();
//		button_1.setIcon(new ImageIcon(HXImageLoader.image_zoom_in));
//		button_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				camera.incrementZoom();
//			}
//		});
//		button_1.setBounds(100, 160, 60, 60);
//		this.add(button_1);
		//...
		
		this.world = new HXWorld(ST3MasterWindow.WORLD_WIDTH, ST3MasterWindow.WORLD_HEIGHT, this);
		this.clock = new HXClock(this);
		this.camera = new ST3Camera(this);
	}
	
	public void paint(Graphics g) {
		// - Render setup
		environment = createImage(getWidth(), getHeight());
		graphics = environment.getGraphics();
		paintComponent(graphics);
		g.drawImage(environment, 0, 0, null);
	}
	
	/**
	 * Called by repaintWorld(), which is called by the HXClock
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// - Panel background
		
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Applies panning from camera
		g.translate(-camera.getCamera_pos_x() + this.getWidth()/2, -camera.getCamera_pos_y() + this.getHeight()/2); // add these based on camera
		
		for (int i = world.getEntities().size() - 1; i >= 0; i--)
			world.getEntities().get(i).draw(g, interpolation);
	}
	
	/**
	 * Marks a visual pin on the map at the given location.
	 * @param loc - The java.awt Point class location of marked point
	 */
	public void addPin(Point loc) {
		new MapPin((int) loc.getX(), (int) loc.getY(), world);
	}
	/**
	 * Marks visual pins on the map at the array of given locations.
	 * @param locs - The java.awt Point class location of marked points
	 */
	public void addPins(Point[] locs) {
		for (Point p : locs) {
			new MapPin((int) p.getX(), (int) p.getY(), world);
		}
	}
	/**
	 * Removes all the current pins marked on the map.
	 */
	public void clearPins() {
		for (HXEntity e : world.getEntities()) {
			if (e instanceof MapPin) {
				e.remove();
			}
		}
	}
	
	public void testDataPins() {
		new Thread() {
			public void run() {
				for (int x = 0; x < 90; x++) {
					new MapPin(HXStartup.rand.nextInt(world.getWidth()), HXStartup.rand.nextInt(world.getHeight()), world);
					try { Thread.sleep(20); } catch (InterruptedException e) { }
				}
			}
		}.start();
	}
	public void clearTestDataPins() {
		for (HXEntity e : world.getEntities()) {
			if (e instanceof MapPin) {
				e.remove();
			}
		}
	}
	
	public void updateWorld() {
		camera.updatePanning();
		world.updateEntities();
	}
	
	public void newWorld() {
		world = new HXWorld(ST3MasterWindow.WORLD_WIDTH, ST3MasterWindow.WORLD_HEIGHT, this);
		camera = new ST3Camera(this);
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
	public ST3Camera getCamera() {
		return camera;
	}

	@Override
	public void repaintWorld(float withInterpolation) {
		setInterpolation(withInterpolation);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		world.mousePress(
				e.getX() + camera.getCamera_pos_x() - this.getWidth()/2, 
				e.getY() + camera.getCamera_pos_y() - this.getHeight()/2);
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mouse_x_last = e.getX();
		mouse_y_last = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setCursor(Cursor.getDefaultCursor());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		camera.dragPan((int) mouse_x_last - e.getX(), (int) mouse_y_last - e.getY());
		mouse_x_last = e.getX();
		mouse_y_last = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		camera.scrollZoom(e.getUnitsToScroll());		
	}
	@Override
	public boolean isOptimizedDrawingEnabled() {
		return false;
	}
}
