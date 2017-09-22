package mp3lib;

import java.awt.EventQueue;
import java.nio.file.Paths;

import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Methods.loadLibrary(Paths.get(System.getProperty("user.home") + System.getProperty("file.separator") + "Music"));
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
