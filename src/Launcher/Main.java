package Launcher;

import View.MainWindow;
import Controller.Controller;

import javax.swing.SwingUtilities;


public class Main {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(new Controller());
			}
		});
	}
}