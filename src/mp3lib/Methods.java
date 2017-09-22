package mp3lib;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

public class Methods {

	static boolean libSaved = false;
	static ArrayList<Path> filePaths = new ArrayList<Path>(); // Create an ArrayList of Strings.
	
	static void loadLibrary(Path dir) {
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir)) { // Create a new directory stream of specified dir.
			for (Path file : dirStream) { // Check every file in dir for extension.
				String extensionCheck = FilenameUtils.getExtension(file.toString());
				if (extensionCheck.equals("mp3")) { // If the file is an mp3 add it to the earlier initialised ArrayList.
					filePaths.add(file);
				}
			}
			libSaved = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(filePaths);
	}
	
	public static void open() {
		// Create new JFileChooser and set the default directory to the "Music" directory.
		JFileChooser openChooser = new JFileChooser(System.getProperty("user.home") + System.getProperty("file.separator") + "Music");
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("MP3 Files", "mp3"); // Set the chooser to only show mp3 files.
		openChooser.setFileFilter(extensionFilter); // Add the ExtensionFilter to the chooser.
		
		int returned = openChooser.showOpenDialog(openChooser); // Open the chooser dialog window.
		
		// Check if the file chosen gets approved.
		if (returned == JFileChooser.APPROVE_OPTION) {
			boolean importApproved = false; // Declare and initiate the boolean imprtApproved.
			File openedFile = openChooser.getSelectedFile(); // Save the selected file in the variable "openedFile"
			String fileName = openedFile.getName(); // Save the name of the opened file as a String.
			for (String s : Frame.nameList) { // Check if the file we're trying to open is already present in the library to prevent it from having duplicates.
				if (s.equals(fileName)) {
					importApproved = false;
					System.err.println("Unable to open file: the file you are trying to open is already in the library."); // Show error message when file is already present.
					break; // Break loop to preserve importApproved's value.
				} else {
					importApproved = true;
				}
			}
			if (importApproved) { // Check if import has been approved and add the opened file to the existing library.
				Frame.nameList.add(fileName);
				System.out.println(Frame.nameList);
			}
			System.out.println(openedFile);
		}
	}
}
