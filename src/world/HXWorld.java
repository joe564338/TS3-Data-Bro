package world;


import java.util.concurrent.CopyOnWriteArrayList;
import main.engine.HXClockUpdater;
import ui.HXViewPanel;
import ui.HXWorldPanel;
import world.entities.HXEntityTemplate;

public class HXWorld implements HXClockUpdater{
	
	// HXViewPanel allows a world to be larger than its parent window 
	// as it can be scrolled and moved like a camera's perspective.
	public static final int WORLD_WIDTH = 2000;
	public static final int WORLD_HEIGHT = 1000;
	
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
		
		// Run anything at start of world...
		for (int x = 0; x < WORLD_WIDTH/50; x++) {
			for (int y = 0; y < WORLD_HEIGHT/50; y++) {
				// This is making the visual grid in the world
				new HXEntityTemplate(x*50, y*50, 0, 0, this);
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

	@Override
	public void updateEntities() {
		
		HXViewPanel x = (HXViewPanel) parentPanel.getParent();
		x.update();
		
		for (HXEntity e : entities)
			e.update();
	}
}