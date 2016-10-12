package main;

import java.awt.EventQueue;
import java.util.Random;

import ui.HXMasterWindow;

public class HXStartup {

	public static Random rand = new Random();
	public static HXMasterWindow masterWindow;

	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					new HXImageLoader();
					masterWindow = new HXMasterWindow();
					masterWindow.pack();
					masterWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
