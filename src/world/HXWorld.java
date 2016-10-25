package world;


import java.util.concurrent.CopyOnWriteArrayList;

import main.engine.HXClockUpdater;
import ui.ST3WorldPanel;
import world.entities.*;

public class HXWorld implements HXClockUpdater{
	
	private double scale = 1;
	private int width;
	private int height;
	private int width_scaled;
	private int height_scaled;
	
	/* === Updates and drawing === */
	private final CopyOnWriteArrayList<HXEntity> entities = new CopyOnWriteArrayList<HXEntity>();
	
	/**
	 * The HXWorld object owns all entities in a CopyOnWriteArrayList but is drawn in a HXWorldPanel.
	 * <p>
	 * @param parentPanel - The JPanel that draws the world.
	 */
	public HXWorld(int w, int h, ST3WorldPanel parentPanel) {
		this.width = w;
		this.height = h;
		this.width_scaled = (int) (w * scale);
		this.height_scaled = (int) (h * scale);
		
		// Run anything at start of world...
		new BackgroundMap(this);
		
		for (int x = 0; x < 57; x++) {
			for (int y = 0; y < 30; y++) {
				// This is making the visual grid in the world
				new MapSpace(x*25, y*25, this);
			}
		}
		// ...
		
	}
	
	public void mousePress(int x, int y) {
//		System.out.println("Mouse press on world! (" + x + ", " + y + ")");
		for (HXEntity e : entities) {
			if (e instanceof HXClickable) {
//				System.out.println("    Position of data point: (" + e.getRect().getX() + ", " + e.getRect().getY() + ")");
				if (e.getRect().contains(x, y)) {
					((HXClickable) e).mouseIntersection();
				}
			}
		}
	}
	
	public CopyOnWriteArrayList<HXEntity> getEntities() {
		return entities;
	}

	@Override
	public void updateEntities() {
		for (HXEntity e : entities) {
			e.update();
		}
	}
	
	public void setScale(double scale) {
		this.scale = scale;
		this.width_scaled = (int) (this.width * scale);
		this.height_scaled = (int) (this.height * scale);
		for (HXEntity e : entities) {
			e.setDraw_scale(scale);
		}
	}
	public double getScale() {
		return this.scale;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth_scaled() {
		return width_scaled;
	}
	public void setWidth_scaled(int width_scaled) {
		this.width_scaled = width_scaled;
	}
	public int getHeight_scaled() {
		return height_scaled;
	}
	public void setHeight_scaled(int height_scaled) {
		this.height_scaled = height_scaled;
	}
}