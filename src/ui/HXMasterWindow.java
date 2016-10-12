package ui;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Color;

import input.HXKey;

public class HXMasterWindow extends JFrame {
	
	// The dimensions of the window
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 394;
	// The dimensions of the panel where the world is rendered
	public static final int VIEWPANEL_WIDTH = 800;
	public static final int VIEWPANEL_HEIGHT = 336;
	
	/**
	 * The primary window for the application.
	 */
	public HXMasterWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the window. 
	 * <p>
	 * This includes attributes of the window and its components
	 * as well as any keys the window should listen to.
	 */
	private void initialize() {
		// Attributes of the parent window
		getContentPane().setBackground(Color.WHITE);
		setTitle("Window Title");
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setResizable(false);
		
		
		// The viewpanel that renders the 2D world
		HXViewPanel viewPanel = new HXViewPanel(VIEWPANEL_WIDTH, VIEWPANEL_HEIGHT, 200, 100);
		getContentPane().add(viewPanel);
		
		
		// === Initialize key bindings for keys listed in the HXKey.KEYS HashMap ===
		InputMap in = viewPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = viewPanel.getActionMap();
		// Iterate through keys in HXKey.KEYS HashMap
		for (String key : HXKey.KEYS.keySet()) {
			// Put actionMapKey into inputmap and actionmap for pressed
			in.put(KeyStroke.getKeyStroke(key.toUpperCase()), "do_" + key + "_pressed");
			am.put("do_" + key + "_pressed", new AbstractAction() {
				// Add method for key pressed down, letting isPressed() in HXKey return true
				@Override
				public void actionPerformed(ActionEvent e) {
					HXKey.KEYS.put(key, true);
				}
			});
			// Put actionMapKey into inputmap and actionmap for released
			in.put(KeyStroke.getKeyStroke("released " + key.toUpperCase()), "do_" + key + "_released");
			am.put("do_" + key + "_released", new AbstractAction(){
				// Add method for key released up, letting isPressed() in HXKey return false
				@Override
				public void actionPerformed(ActionEvent e) {
					HXKey.KEYS.put(key, false);
				}
			});
		}
	}
}
