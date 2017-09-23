package mp3lib;

import java.awt.EventQueue;
import java.awt.Point;
import java.nio.file.Paths;

import javax.swing.UIManager;

public class Main {

	static Frame frame;
	
	public static void main(String[] args) {
		Methods.initialize();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Methods.loadLibrary(Paths.get(Methods.dirPath));
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void refreshFrame() {
		Point framePoint = frame.getLocationOnScreen(); // Get current frame location.
		frame.dispose(); // Delete current frame.
		frame = new Frame(); // Create new frame.
		frame.setLocation(framePoint); // Set new frame's location equal to old frame's location.
		frame.setVisible(true); // Make the new frame visible.
	}
}
