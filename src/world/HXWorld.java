package world;


import java.util.concurrent.CopyOnWriteArrayList;
import main.engine.HXClockUpdater;
import ui.HXWorldPanel;
import world.entities.BackgroundMap;
import world.entities.MapSpace;

public class HXWorld implements HXClockUpdater{
	
	// HXViewPanel allows a world to be larger than its parent window 
	// as it can be scrolled and moved like a camera's perspective.
	public static final int WORLD_WIDTH = 1425;
	public static final int WORLD_HEIGHT = 742;
	
	private int widthScaled;
	private int heightScaled;
	
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
		this.widthScaled = WORLD_WIDTH * parentPanel.getZoom();
		this.heightScaled = WORLD_HEIGHT * parentPanel.getZoom();
		
		// Run anything at start of world...
		for (int x = 0; x < 57; x++) {
			for (int y = 0; y < 30; y++) {
				// This is making the visual grid in the world
				new MapSpace(x*25, y*25, this);
			}
		}
		
		new BackgroundMap(this);
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

	@Override
	public void updateEntities() {
		for (HXEntity e : entities) {
			e.update();
		}
	}

	public void updateZoom(int zoom) {
		widthScaled = WORLD_WIDTH * zoom;
		heightScaled = WORLD_HEIGHT * zoom;
		for (HXEntity e : entities)
			e.rescaleSize(zoom);
	}
	
	public int getWorldWidthScaled() {
		return widthScaled;
	}

	public int getWorldHeightScaled() {
		return heightScaled;
	}
}