package mp3lib;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class Frame extends JFrame implements ActionListener {
	 
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	static JList libList;
	static JScrollPane libPane;
	
	static ArrayList<String> nameList = new ArrayList<String>(); // Create new ArrayList of type String.

	/**
	 * Create the frame.
	 */
	public Frame() {
		// Set frame properties.
		super("MP3 Player");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1123, 801);
		
		// Declare all the frame components.
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(open);
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		JMenuItem addFiles = new JMenuItem("Add Files");
		addFiles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(addFiles);
		
		JMenuItem addFolder = new JMenuItem("Add Folder");
		addFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(addFolder);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem preferences = new JMenuItem("Preferences");
		preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		preferences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PreferenceFrame preferences = new PreferenceFrame();
				preferences.setVisible(true);
			}
		});
		mnFile.add(preferences);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem exit = new JMenuItem("Exit");
		mnFile.add(exit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0);
		contentPane.add(splitPane);
		
		libPane = new JScrollPane();
		splitPane.setLeftComponent(libPane);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setResizeWeight(0.666);
		splitPane.setRightComponent(splitPane_1);
		
		JPanel imgPanel = new JPanel();
		splitPane_1.setLeftComponent(imgPanel);
		imgPanel.setLayout(null);
		
		JLabel tmpLabel = new JLabel("<IMG OF MP3 FILE GOES HERE>");
		tmpLabel.setBounds(163, 156, 191, 16);
		imgPanel.add(tmpLabel);
		
		JPanel detailsPanel = new JPanel();
		splitPane_1.setRightComponent(detailsPanel);
		detailsPanel.setLayout(null);
		
		JLabel tmpLabel2 = new JLabel("<DETAILS OF MP3 FILE GO HERE>");
		tmpLabel2.setBounds(157, 109, 222, 16);
		detailsPanel.add(tmpLabel2);
		
		// Create array of all components.
		JMenuItem[] items = { open, addFiles, addFolder, preferences, exit };
		
		// Go through every component and add an ActionListener to said component.
		for (JMenuItem item : items) {
			item.addActionListener(this);
		}
		
		initialize();
	}
	
	public void initialize() {
		if (Methods.libSaved) { // Check if the directory has been scanned and saved into memory.
			
			nameList.clear();
			
			for (Path item : Methods.filePaths) {
					nameList.add(item.getFileName().toString()); // For every file in the saved directory add it's name as a string to the ArrayList 'nameList'.
			}
			
			libList = new JList(nameList.toArray()); // Create new JList object with the values of 'nameList'
			libPane.setViewportView(libList); // Draw the JList on the screen.
			
			MouseListener mouse = new MouseAdapter() { 
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1) { // Check for a single-click on an item from the list.
						// >>USE THIS TO SHOW THE IMAGE AND DETAILS OF SELECTED MP3<<
					}
					
					if (e.getClickCount() == 2) { // Check for a double-click on an item from the list.
						String selectedFile = (String) libList.getSelectedValue(); // Get the name of the item clicked on.
						System.out.println("Name: " + selectedFile);
						for (String s : nameList) { // Check every name in nameList to see if it equals the selectedFile.
							if (s.equals(selectedFile)) {
								int nameListIndex = nameList.indexOf(s); // Get the index of the selectedFile
								System.out.println("Index: " + nameListIndex);
								System.out.println("Path: " + Methods.filePaths.get(nameListIndex));
								// >>Use this to call the player window and play the selected file.<<
							}
						}
					}
				}
			};
			
			libList.addMouseListener(mouse);
			
		} else {
			System.err.println("Shit's fucked... For some reason we couldn't load the files in the specified library folder! :(");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command.equals("Open")) {
			Methods.openFile();
		}
		if (command.equals("Exit")) {
			System.exit(0);
		}
	}
}
