package ui;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import input.HXKey;
import main.HXImageLoader;

public class ST3MasterWindow extends JFrame {
	
	// The dimensions of the window
	public static final int WINDOW_WIDTH = 712;
	public static final int WINDOW_HEIGHT = 428;
	// The dimensions of the panel where the world is rendered
	public static final int VIEWPANEL_WIDTH = 712;
	public static final int VIEWPANEL_HEIGHT = 371;
	// The dimensions of the world inside the world panel
	public static final int WORLD_WIDTH = 1425;
	public static final int WORLD_HEIGHT = 742;
	
	/**
	 * The primary window for the application.
	 */
	public ST3MasterWindow() {
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
		ST3WorldPanel worldPanel = new ST3WorldPanel(VIEWPANEL_WIDTH, VIEWPANEL_HEIGHT, 0, 0);
		getContentPane().add(worldPanel);
		
		// Other user interface components...
		JButton button = new JButton();
		button.setIcon(new ImageIcon(HXImageLoader.image_zoom_out));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldPanel.getCamera().decrementZoom();
			}
		});
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setBounds(644, 305, 60, 60);
		getLayeredPane().add(button, 1, 0);
		
		JButton button_1 = new JButton();
		button_1.setIcon(new ImageIcon(HXImageLoader.image_zoom_in));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldPanel.getCamera().incrementZoom();
			}
		});
		button_1.setBorderPainted(false);
		button_1.setContentAreaFilled(false);
		button_1.setBounds(644, 240, 60, 60);
		getLayeredPane().add(button_1, 1, 0);
		
		JButton button_2 = new JButton("Add Data Pins");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldPanel.testDataPins();
			}
		});
		button_2.setBounds(69, 374, 120, 30);
		getContentPane().add(button_2);
		
		JButton button_3 = new JButton("Clear Data Pins");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldPanel.clearTestDataPins();
			}
		});
		button_3.setBounds(189, 374, 120, 30);
		getContentPane().add(button_3);
		// ...
		
		// === Initialize key bindings for keys listed in the ST3Key.KEYS HashMap ===
		InputMap in = worldPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = worldPanel.getActionMap();
		// Iterate through keys in ST3Key.KEYS HashMap
		for (String key : HXKey.KEYS.keySet()) {
			// Put actionMapKey into inputmap and actionmap for pressed
			in.put(KeyStroke.getKeyStroke(key.toUpperCase()), "do_" + key + "_pressed");
			am.put("do_" + key + "_pressed", new AbstractAction() {
				// Add method for key pressed down, letting isPressed() in ST3Key return true
				@Override
				public void actionPerformed(ActionEvent e) {
					HXKey.KEYS.put(key, true);
				}
			});
			// Put actionMapKey into inputmap and actionmap for released
			in.put(KeyStroke.getKeyStroke("released " + key.toUpperCase()), "do_" + key + "_released");
			am.put("do_" + key + "_released", new AbstractAction(){
				// Add method for key released up, letting isPressed() in ST3Key return false
				@Override
				public void actionPerformed(ActionEvent e) {
					HXKey.KEYS.put(key, false);
				}
			});
		}
	}
}
