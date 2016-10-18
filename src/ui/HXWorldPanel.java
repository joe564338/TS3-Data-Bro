package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import input.HXKey;
import main.HXStartup;
import main.engine.HXClock;
import main.engine.HXClockRenderer;
import world.HXEntity;
import world.HXWorld;
import world.entities.DataPin;

public class HXWorldPanel extends JPanel implements HXClockRenderer, MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	/* === Movement key constants === */
	private final int DIRECTION_RIGHT = -1;
	private final int DIRECTION_LEFT  = 1;
	private final int DIRECTION_UP	  = 1;
	private final int DIRECTION_DOWN  = -1;
	private final String KEY_LEFT  = "d";
	private final String KEY_RIGHT = "g";
	private final String KEY_UP    = "r";
	private final String KEY_DOWN  = "f";
	
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
	
	/* === Camera movement === */
	private final int CAM_VEL_TERMINAL = 50;
	private final int CAM_ACCEL = 3;
	private final int CAM_FRICTION = 3;
	private int camera_pos_x;
	private int camera_pos_y;
	private int camera_vel_x = 0;
	private int camera_vel_y = 0;
	private int camera_velModifier_x = 1;
	private int camera_velModifier_y = 1;
	
	private final int ZOOM_MAX = 20;
	private final int ZOOM_MIN = 1;
	private int zoom = 1;
	
	/**
	 * Extension of a JPanel that contains an HXWorld and paints all of this object's entities.
	 */
	public HXWorldPanel(int width, int height, int worldStartX, int worldStartY) {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, width, height);
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// Does not properly shift when constructing a HXWorldPanel with worldStart offset
		this.camera_pos_x = worldStartX;
		this.camera_pos_y = worldStartY;
//		this.camera_pos_x = (camera_pos_x + this.getWidth()/2) * zoom;
//		this.camera_pos_y = (camera_pos_y + this.getHeight()/2) * zoom;
		this.world = new HXWorld(this);
		this.clock = new HXClock(this);
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
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// - Panel background
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// - Entity's added to graphics
//		for (HXEntity e: world.getEntities())
//			e.draw(g, interpolation);
		
		for (int i = world.getEntities().size() - 1; i >= 0; i--)
			world.getEntities().get(i).draw(g, interpolation);
		
//		// - Panel border
//		g.setColor(Color.black);
//		g.drawRect(0, 0, getWidth()-1, getHeight()-1);	
	}
	
	public void updateCamera(){
		// increases acceleration if any of the panning keys are held down and adjusts direction based on which specific key.
		if (HXKey.isPressed(KEY_LEFT) || HXKey.isPressed(KEY_RIGHT)) {
			// Keeps x velocity within terminal constant
			camera_vel_x = Math.min(camera_vel_x + CAM_ACCEL, CAM_VEL_TERMINAL);
			if (HXKey.isPressed(KEY_RIGHT))
				camera_velModifier_x = DIRECTION_RIGHT;
			else if (HXKey.isPressed(KEY_LEFT))
				camera_velModifier_x = DIRECTION_LEFT;
		} else {
			// Keeps x velocity above zero
			camera_vel_x = Math.max(camera_vel_x - CAM_FRICTION, 0);
		}
		if (HXKey.isPressed(KEY_UP) || HXKey.isPressed(KEY_DOWN)) {
			// Keeps y velocity within terminal constant
			camera_vel_y = Math.min(camera_vel_y + CAM_ACCEL, CAM_VEL_TERMINAL);
			if (HXKey.isPressed(KEY_UP))
				camera_velModifier_y = DIRECTION_UP;
			else if (HXKey.isPressed(KEY_DOWN))
				camera_velModifier_y = DIRECTION_DOWN;
		} else {
			// Keeps y velocity above zero
			camera_vel_y = Math.max(camera_vel_y - CAM_FRICTION, 0);
		}
		
		if (camera_vel_x > 0 || camera_vel_y > 0) {
			// Move camera by this amount
			int wX = camera_pos_x + camera_vel_x * camera_velModifier_x;
			int wY = camera_pos_y + camera_vel_y * camera_velModifier_y;
			// Ensure calculations are within world bounds
			camera_pos_x = (int) Math.max(Math.min(wX, 0), -(world.getWorldWidthScaled() - this.getWidth()));
			camera_pos_y = (int) Math.max(Math.min(wY, 0), -(world.getWorldHeightScaled() - this.getHeight()));
			// Update all entities
			for (HXEntity e : world.getEntities()) {
				e.panShift(camera_pos_x, camera_pos_y);
			}
		}
	}
	
	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
		// The following variable updates must happen before world.updateZoom is called otherwise getWorldScaled will be updated
		camera_pos_x = (camera_pos_x - this.getWidth()/2) * (HXWorld.WORLD_WIDTH * zoom) / world.getWorldWidthScaled() + this.getWidth()/2;
		camera_pos_y = (camera_pos_y - this.getHeight()/2) * (HXWorld.WORLD_HEIGHT * zoom) / world.getWorldHeightScaled() + this.getHeight()/2;
		world.updateZoom(zoom);
		camera_pos_x = (int) Math.max(Math.min(camera_pos_x, 0), -(world.getWorldWidthScaled() - this.getWidth()));
		camera_pos_y = (int) Math.max(Math.min(camera_pos_y, 0), -(world.getWorldHeightScaled() - this.getHeight()));
		// Update entities
		for (HXEntity e : world.getEntities()) {
			e.panShift(camera_pos_x, camera_pos_y);
		}
	}
	public void incrementZoom() {
		if (zoom < ZOOM_MAX) {
			zoom++;
			// The following variable updates must happen before world.updateZoom is called otherwise getWorldScaled will be updated
			camera_pos_x = (camera_pos_x - this.getWidth()/2) * (HXWorld.WORLD_WIDTH * zoom) / world.getWorldWidthScaled() + this.getWidth()/2;
			camera_pos_y = (camera_pos_y - this.getHeight()/2) * (HXWorld.WORLD_HEIGHT * zoom) / world.getWorldHeightScaled() + this.getHeight()/2;
			world.updateZoom(zoom);
			camera_pos_x = (int) Math.max(Math.min(camera_pos_x, 0), -(world.getWorldWidthScaled() - this.getWidth()));
			camera_pos_y = (int) Math.max(Math.min(camera_pos_y, 0), -(world.getWorldHeightScaled() - this.getHeight()));
			// Update entities
			for (HXEntity e : world.getEntities()) {
				e.panShift(camera_pos_x, camera_pos_y);
			}
		}
	}
	public void decrementZoom() {
		if (zoom > ZOOM_MIN) {
			zoom--;
			// The following variable updates must happen before world.updateZoom is called otherwise getWorldScaled will be updated
			camera_pos_x = (camera_pos_x - this.getWidth()/2) * (HXWorld.WORLD_WIDTH * zoom) / world.getWorldWidthScaled() + this.getWidth()/2;
			camera_pos_y = (camera_pos_y - this.getHeight()/2) * (HXWorld.WORLD_HEIGHT * zoom) / world.getWorldHeightScaled() + this.getHeight()/2;
			world.updateZoom(zoom);
			camera_pos_x = (int) Math.max(Math.min(camera_pos_x, 0), -(world.getWorldWidthScaled() - this.getWidth()));
			camera_pos_y = (int) Math.max(Math.min(camera_pos_y, 0), -(world.getWorldHeightScaled() - this.getHeight()));
			// Update entities
			for (HXEntity e : world.getEntities()) {
				e.panShift(camera_pos_x, camera_pos_y);
			}
		}
	}
	
	public void addDataPoints() {
		new Thread() {
			public void run() {
				for (int x = 0; x < 90; x++) {
					new DataPin(HXStartup.rand.nextInt(HXWorld.WORLD_WIDTH), HXStartup.rand.nextInt(HXWorld.WORLD_HEIGHT), world);
					try { Thread.sleep(20); } catch (InterruptedException e) { }
				}
			}
		}.start();
	}
	public void clearDataPoints() {
		for (HXEntity e : world.getEntities()) {
			if (e instanceof DataPin) {
				e.remove();
			}
		}
	}
	
	public void updateWorld() {
		updateCamera();
		world.updateEntities();
		
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
	
	public int getCameraPosX() {
		return camera_pos_x;
	}
	public int getCameraPosY() {
		return camera_pos_y;
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
		world.mousePress(e.getX() - camera_pos_x, e.getY() - camera_pos_y);
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
		world.mousePress(e.getX() - camera_pos_x, e.getY() - camera_pos_y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
