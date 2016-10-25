package ui;

public class ST3Camera {
	
	/* === Camera movement === */
	private final int CAM_VEL_TERMINAL = 120;
	private final int CAM_FRICTION = 15;
	private final double CAM_MODIFIER = 0.5;
	private final int CAM_VIEW_BORDER = 50; // allows camera to leave map bounds by this value on ea. side
	// Camera X,Y position is point camera is centered on
	private int camera_pos_x;
	private int camera_pos_y;
	private double camera_speedSquared = 0; //magnitude
	private double camera_direction = 0; //direction
	// Width, Height same as the viewpanels size
	private int camera_width;
	private int camera_height;
	
	private final int ZOOM_MAX = 20;
	private final int ZOOM_MIN = 1;
	private final double ZOOM_INCREMENT = 0.1;
	private double zoom = 1;
	
	private ST3WorldPanel parentPanel;
	
	/**
	 * Controls the panning and zooming of the ST3World entities.
	 * Abstracted from HXWorldPanel for clarity and organization.
	 * Will only be created once by a ST3WorldPanel.
	 */
	public ST3Camera(ST3WorldPanel worldParent) {
		this.parentPanel = worldParent;
		this.camera_width = worldParent.getWidth();
		this.camera_height = worldParent.getHeight();
		this.camera_pos_x = camera_width/2;
		this.camera_pos_y = camera_height/2;
	}
	
	// MARK: Panning ====================================
	
	/**
	 * Called only by ST3WorldPanel mouseDragged interface
	 */
	public void dragPan(int vel_x, int vel_y) {
		// Get velocity vector from passed mouse velocity
		camera_direction = Math.atan2(vel_y, vel_x);
		// Clamp speed to terminal velocity and don't square root to save CPU
		camera_speedSquared = Math.min(Math.pow(vel_x, 2) + Math.pow(vel_y, 2), CAM_VEL_TERMINAL);
	}
	
	/**
	 * Called only by the updateWorld method in parent panel. From ST3Clock
	 */
	public void updatePanning() {
		if (camera_speedSquared > 0) {
			// Move camera by velocity vector
			camera_pos_x += camera_speedSquared * Math.cos(camera_direction) * CAM_MODIFIER;
			camera_pos_y += camera_speedSquared * Math.sin(camera_direction) * CAM_MODIFIER;
			// Apply friction for camera deceleration
			camera_speedSquared -= CAM_FRICTION;
			if (camera_speedSquared <= 0) {
				camera_speedSquared = 0;
			}
			// Ensure calculations are within world bounds
			borderCheck();
		}
	}
	
	/**
	 * Class internal use only <br>
	 * Abstracted out from updatePanning() so the zoom modifying methods can ensure the camera stays in the given bounds.
	 */
	private void borderCheck() {
		double leftBorder  = camera_width/2 - CAM_VIEW_BORDER * zoom;
		double topBorder   = camera_height/2 - CAM_VIEW_BORDER * zoom;
		double rightBorder = parentPanel.getWorld().getWidth_scaled() - camera_width/2 + CAM_VIEW_BORDER * zoom;
		double botBorder   = parentPanel.getWorld().getHeight_scaled() - camera_height/2 + CAM_VIEW_BORDER * zoom;
		if (camera_pos_x < leftBorder) {
			camera_pos_x = (int) leftBorder;
		} else if (camera_pos_x > rightBorder) {
			camera_pos_x = (int) rightBorder;
		}
		if (camera_pos_y < topBorder) {
			camera_pos_y = (int) topBorder;
		} else if (camera_pos_y > botBorder) {
			camera_pos_y = (int) botBorder;
		}
	}
	
	// Mark: Zooming ====================================
	public void setZoom(double zoom) {
		if (zoom < ZOOM_MAX && zoom > ZOOM_MIN) {
			this.zoom = zoom;
			updateWorldZoom();
		}
	}
	public void incrementZoom() {
		if (zoom < ZOOM_MAX) {
			zoom += ZOOM_INCREMENT;
			updateWorldZoom();
		}
	}
	public void decrementZoom() {
		if (zoom > ZOOM_MIN) {
			zoom -= ZOOM_INCREMENT;
			updateWorldZoom();
		}
	}
	public void scrollZoom(int amount) {
		if (zoom + amount * ZOOM_INCREMENT < ZOOM_MAX && zoom + amount * ZOOM_INCREMENT > ZOOM_MIN) {
			zoom += amount * ZOOM_INCREMENT;
			zoom = Math.min(zoom, ZOOM_MAX);
			zoom = Math.max(zoom, ZOOM_MIN);
			updateWorldZoom();
		}
	}
	
	/**
	 * Class internal use only <br>
	 * Called by all zoom modifying methods to ensure camera stays centered as everything scales up or down.
	 */
	private void updateWorldZoom() {
		double exp1_x = (double) camera_pos_x / parentPanel.getWorld().getWidth_scaled();
		double exp1_y = (double) camera_pos_y / parentPanel.getWorld().getHeight_scaled();
		parentPanel.getWorld().setScale(zoom);
		camera_pos_x = (int) (exp1_x * parentPanel.getWorld().getWidth_scaled());
		camera_pos_y = (int) (exp1_y * parentPanel.getWorld().getHeight_scaled());
		borderCheck();
	}
	
	// ============================   MARK: Getters and Setters ============================ 
	public double getZoom() {
		return zoom;
	}
	public int getCamera_pos_x() {
		return camera_pos_x;
	}
	public void setCamera_pos_x(int camera_pos_x) {
		this.camera_pos_x = camera_pos_x;
	}
	public int getCamera_pos_y() {
		return camera_pos_y;
	}
	public void setCamera_pos_y(int camera_pos_y) {
		this.camera_pos_y = camera_pos_y;
	}
}
