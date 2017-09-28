package mp3lib;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Frame extends JFrame implements ActionListener {
	 
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	static JList libList;
	static JScrollPane libPane;
	
	JLabel duration;
	JLabel size;
	JLabel bitrate;
	JLabel title;
	JLabel artist;
	JLabel album;
	JLabel file;
	
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
		
		JLabel titleLabel = new JLabel("Title:");
		titleLabel.setForeground(UIManager.getColor("Button.shadow"));
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		titleLabel.setBounds(10, 11, 50, 16);
		detailsPanel.add(titleLabel);
		
		title = new JLabel("123");
		title.setFont(new Font("Tahoma", Font.PLAIN, 12));
		title.setBounds(80, 12, 140, 14);
		detailsPanel.add(title);
		
		JLabel artistLabel = new JLabel("Artist:");
		artistLabel.setForeground(SystemColor.controlShadow);
		artistLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		artistLabel.setBounds(10, 30, 50, 16);
		detailsPanel.add(artistLabel);
		
		artist = new JLabel("123");
		artist.setFont(new Font("Tahoma", Font.PLAIN, 12));
		artist.setBounds(80, 31, 140, 14);
		detailsPanel.add(artist);
		
		JLabel albumLabel = new JLabel("Album:");
		albumLabel.setForeground(SystemColor.controlShadow);
		albumLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		albumLabel.setBounds(10, 48, 50, 16);
		detailsPanel.add(albumLabel);
		
		album = new JLabel("123");
		album.setFont(new Font("Tahoma", Font.PLAIN, 12));
		album.setBounds(80, 49, 140, 14);
		detailsPanel.add(album);
		
		JLabel duratLabel = new JLabel("Duration:");
		duratLabel.setForeground(SystemColor.controlShadow);
		duratLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		duratLabel.setBounds(10, 66, 50, 16);
		detailsPanel.add(duratLabel);
		
		JLabel sizeLabel = new JLabel("Size:");
		sizeLabel.setForeground(SystemColor.controlShadow);
		sizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sizeLabel.setBounds(10, 84, 50, 16);
		detailsPanel.add(sizeLabel);
		
		JLabel bitRateLabel = new JLabel("Bit rate:");
		bitRateLabel.setForeground(SystemColor.controlShadow);
		bitRateLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		bitRateLabel.setBounds(10, 102, 50, 16);
		detailsPanel.add(bitRateLabel);
		
		duration = new JLabel("123");
		duration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		duration.setBounds(80, 68, 140, 14);
		detailsPanel.add(duration);
		
		size = new JLabel("123");
		size.setFont(new Font("Tahoma", Font.PLAIN, 12));
		size.setBounds(80, 86, 140, 14);
		detailsPanel.add(size);
		
		bitrate = new JLabel("123");
		bitrate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		bitrate.setBounds(80, 104, 140, 14);
		detailsPanel.add(bitrate);
		
		JButton btnPlay = new JButton("|>");
		btnPlay.setBounds(410, 11, 45, 35);
		detailsPanel.add(btnPlay);
		
		JButton btnPauze = new JButton("||");
		btnPauze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnPauze.setBounds(465, 11, 45, 35);
		detailsPanel.add(btnPauze);
		
		JButton btnStop = new JButton("[ ]");
		btnStop.setBounds(355, 11, 45, 35);
		detailsPanel.add(btnStop);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(355, 48, 155, 14);
		detailsPanel.add(progressBar);
		
		JLabel fileLabel = new JLabel("File:");
		fileLabel.setForeground(SystemColor.controlShadow);
		fileLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fileLabel.setBounds(10, 119, 50, 16);
		detailsPanel.add(fileLabel);
		
		file = new JLabel("123");
		file.setFont(new Font("Tahoma", Font.PLAIN, 12));
		file.setBounds(80, 121, 140, 14);
		detailsPanel.add(file);
		
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
					
					String selectedFile = (String) libList.getSelectedValue(); // Get the name of the item clicked on.
					
					if (e.getClickCount() == 1) { // Check for a single-click on an item from the list.
						for (String s : nameList) { // Check every name in nameList to see if it equals the selectedFile.
							if (s.equals(selectedFile)) {
								int nameListIndex = nameList.indexOf(s); // Get the index of the selectedFile
								try {
									File mp3file = new File(Methods.filePaths.get(nameListIndex).toString());
									Mp3File mp3File = new Mp3File(Methods.filePaths.get(nameListIndex).toString()); // Get the selected mp3 file.
									ID3v2 id3v2Tag = mp3File.getId3v2Tag(); // Get the ID3v2 tags of selected mp3 file.
									if (id3v2Tag.getTitle() != null) {
										title.setText(id3v2Tag.getTitle());
										title.setSize(title.getPreferredSize());
									} else {
										title.setText("n/a");
									}
									if (id3v2Tag.getArtist() != null) {
										artist.setText(id3v2Tag.getArtist());
									} else {
										artist.setText("n/a");
									}
									if (id3v2Tag.getAlbum() != null) {
										album.setText(id3v2Tag.getAlbum());
									} else {
										album.setText("n/a");
									}
									duration.setText(String.valueOf(mp3File.getLengthInSeconds()) + " seconds");
									
									DecimalFormat df = new DecimalFormat("#.##");
									double fileSizeInBytes = mp3file.length(); // Get length of file in bytes
									double fileSizeInKB = fileSizeInBytes / 1024; // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
									double fileSizeInMB = fileSizeInKB / 1024; // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
									size.setText(String.valueOf(df.format(fileSizeInMB)) + "mb");
									
									bitrate.setText(String.valueOf(mp3File.getBitrate()) + "kbps");
									
									file.setText(Methods.filePaths.get(nameListIndex).toString());
									file.setSize(file.getPreferredSize());
								} catch (UnsupportedTagException e1) {
									e1.printStackTrace();
								} catch (InvalidDataException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
						// >>USE THIS TO SHOW THE IMAGE AND DETAILS OF SELECTED MP3<<
					}
					
					if (e.getClickCount() == 2) { // Check for a double-click on an item from the list.
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
