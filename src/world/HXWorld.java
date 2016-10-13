package world;


import java.util.concurrent.CopyOnWriteArrayList;
import main.engine.HXClockUpdater;
import ui.HXViewPanel;
import ui.HXWorldPanel;
import world.entities.MapSpace;

public class HXWorld implements HXClockUpdater{
	
	// HXViewPanel allows a world to be larger than its parent window 
	// as it can be scrolled and moved like a camera's perspective.
	public static final int WORLD_WIDTH = 1425;
	public static final int WORLD_HEIGHT = 742;
	
	/* === Zooming variables === */
	private double scaledWidth;
	private double scaledHeight;
	
	/* === Updates and drawing === */
	private final CopyOnWriteArrayList<HXEntity> entities = new CopyOnWriteArrayList<HXEntity>();
	private final HXWorldPanel parentPanel;
	
	/**
	 * The HXWorld object owns all entities in a CopyOnWriteArrayList but is drawn in a HXWorldPanel.
	 * <p>
	 * @param parentPanel - The JPanel that draws the world.
	 */
	public HXWorld(HXWorldPanel parentPanel) {
		this.parentPanel = parentPanel;
		this.scaledWidth = parentPanel.getZoom() * WORLD_WIDTH;
		this.scaledHeight = parentPanel.getZoom() * WORLD_HEIGHT;
		
		// Run anything at start of world...
		for (int x = 0; x < 57; x++) {
			for (int y = 0; y < 53; y++) {
				// This is making the visual grid in the world
				new MapSpace(x*25, y*14, x, y, 0, 0, this);
			}
		}
		
		// ...
	}
	
	public void mousePress(int x, int y) {
		for (HXEntity e : entities) {
			if (e instanceof HXClickable) {
				if (e.getRect().contains(x, y)) {
					((HXClickable) e).mouseIntersection();
				}
			}
		}
	}
	
	public CopyOnWriteArrayList<HXEntity> getEntities() {
		return entities;
	}

	public HXWorldPanel getParentPanel() {
		return parentPanel;
	}
	
	public void updateZoom(double zoom) {
		scaledWidth = WORLD_WIDTH * zoom;
		scaledHeight = WORLD_HEIGHT * zoom;
		for (HXEntity e : entities)
			e.rescaleSize(zoom);
	}

	@Override
	public void updateEntities() {
		
		HXViewPanel x = (HXViewPanel) parentPanel.getParent();
		x.update();
		
		for (HXEntity e : entities)
			if (e.getWidth()*parentPanel.getZoom() != 0 || e.getHeight()*parentPanel.getZoom() != 0)
				e.update();
	}

	public double getScaledWidth() {
		return scaledWidth;
	}

	public void setScaledWidth(double scaledWidth) {
		this.scaledWidth = scaledWidth;
	}

	public double getScaledHeight() {
		return scaledHeight;
	}

	public void setScaledHeight(double scaledHeight) {
		this.scaledHeight = scaledHeight;
	}
	
	
}