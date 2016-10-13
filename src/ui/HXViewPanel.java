package ui;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import input.HXKey;
import world.HXWorld;

public class HXViewPanel extends JPanel {
	
	/* === Movement key constants === */
	private final int DIRECTION_RIGHT = -1;
	private final int DIRECTION_LEFT  = 1;
	private final int DIRECTION_UP	  = 1;
	private final int DIRECTION_DOWN  = -1;
	private final String KEY_LEFT  = "d";
	private final String KEY_RIGHT = "g";
	private final String KEY_UP    = "r";
	private final String KEY_DOWN  = "f";
	
	/* === Panel that gets moved; contains all content === */
	private HXWorldPanel worldPanel;
	
	/* === Position variables === */
	private double xAccel = 0;
	private double yAccel = 0;
	private double xDirectionModifier = 0;
	private double yDirectionModifier = 0;
	
	/* === Camera movement constants === */
	private final int ACCEL_TERMINAL = 50;
	private final int ACCEL_INCR = 3;
	private final int ACCEL_DECR = 3;
	
	/**
	 * Serves as an overlaying JPanel that only contains an HXWorldPanel.
	 * <p>
	 * Effectively creates a camera affect so the entire world can be moved but seems like the camera is moving.
	 * @param width - Width of the viewpanel
	 * @param height - Height of the viewpanel
	 * @param worldStartX - The x coordinate in upper left corner of the world in the viewpanel at start. Domain [0, HXWorld.WORLD_WIDTH - WIDTH_MAX]
	 * @param worldStartY - The y coordinate in upper left corner of the world in the viewpanel at start. Domain [0, HXWorld.WORLD_WIDTH - WIDTH_MAX]
	 */
	public HXViewPanel(int width, int height, int worldStartX, int worldStartY) {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, width, height);
		setLayout(null);
		
		// Testing
//		HXKey.KEYS.put(KeyEvent.VK_W, true);
//		HXKey.KEYS.put(KeyEvent.VK_D, true);
		
		this.worldPanel = new HXWorldPanel(worldStartX, worldStartY);
		this.add(worldPanel);
	}

	public void update(){
//		System.out.println("Keys: " + HXKey.KEYS.values());
		// increases acceleration if any of the panning keys are held down and adjusts direction based on which specific key.
		if (HXKey.isPressed(KEY_LEFT) || HXKey.isPressed(KEY_RIGHT)) {
			// Keeps x acceleration within terminal constant
			xAccel = Math.min(xAccel + ACCEL_INCR, ACCEL_TERMINAL);
			if (HXKey.isPressed(KEY_RIGHT))
				xDirectionModifier = DIRECTION_RIGHT;
			else if (HXKey.isPressed(KEY_LEFT))
				xDirectionModifier = DIRECTION_LEFT;
		} else {
			// Keeps x acceleration above zero
			xAccel = Math.max(xAccel - ACCEL_DECR, 0);
		}
		if (HXKey.isPressed(KEY_UP) || HXKey.isPressed(KEY_DOWN)) {
			// Keeps y acceleration within terminal constant
			yAccel = Math.min(yAccel + ACCEL_INCR, ACCEL_TERMINAL);
			if (HXKey.isPressed(KEY_UP))
				yDirectionModifier = DIRECTION_UP;
			else if (HXKey.isPressed(KEY_DOWN))
				yDirectionModifier = DIRECTION_DOWN;
		} else {
			// Keeps y acceleration above zero
			yAccel = Math.max(yAccel - ACCEL_DECR, 0);
		}
		
		if (xAccel > 0 || yAccel > 0) {
			double wX = worldPanel.getLocation().getX() + xAccel * xDirectionModifier;
			double wY = worldPanel.getLocation().getY() + yAccel * yDirectionModifier;
			wX = Math.max(Math.min(wX, 0), -(worldPanel.getWorld().getScaledWidth() - this.getWidth()));
			wY = Math.max(Math.min(wY, 0), -(worldPanel.getWorld().getScaledHeight() - this.getHeight()));
			worldPanel.setLocation((int) wX, (int) wY);
		}
	}
	
	public HXWorldPanel getWorldPanel() {
		return this.worldPanel;
	}
}
