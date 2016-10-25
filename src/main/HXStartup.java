package main;

import java.awt.EventQueue;
import java.util.Random;

import ui.ST3MasterWindow;

public class HXStartup {

	public static Random rand = new Random();
	public static ST3MasterWindow masterWindow;

	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new HXImageLoader();
					masterWindow = new ST3MasterWindow();
					masterWindow.pack();
					masterWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
