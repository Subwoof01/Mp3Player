package mp3lib;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PreferenceFrame extends JDialog implements ActionListener {

	private static final long serialVersionUID = 7866597393969354599L;
	
	private final JPanel contentPanel = new JPanel();
	static JTextField dirField;
	static JButton applyButton;
	static boolean applied = false;

	/**
	 * Create the dialog.
	 */
	public PreferenceFrame() {
		setBounds(100, 100, 450, 300);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblLibraryDirectory = new JLabel("Library directory:");
		lblLibraryDirectory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLibraryDirectory.setBounds(10, 11, 94, 14);
		contentPanel.add(lblLibraryDirectory);
		
		dirField = new JTextField();
		dirField.setBounds(10, 27, 363, 20);
		dirField.getDocument().addDocumentListener(new DocumentEvent());
		contentPanel.add(dirField);
		dirField.setColumns(10);
		
		JButton searchDirButton = new JButton("...");
		searchDirButton.setBounds(375, 27, 28, 20);
		searchDirButton.addActionListener(this);
		contentPanel.add(searchDirButton);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
			applyButton = new JButton("Apply");
			applyButton.addActionListener(this);
			applyButton.setEnabled(false);
			buttonPane.add(applyButton);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command.equals("OK")) {
			if (!applied) { // Check if apply button has been clicked.
				if (!dirField.getText().isEmpty()) { // If the text field is not empty, continue.
					Methods.filePaths.clear(); // Clear filePaths before repopulating it.
					Methods.dirPath = dirField.getText(); // Set dirPath equal to the text field contents.
					try {
						BufferedWriter writer = new BufferedWriter(new FileWriter("lib-dir.txt")); // Create a writer to write to the file lib-dir.txt
						writer.write(Methods.dirPath); // Write the new dirPath to lib-dir.txt
						writer.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					applyButton.setEnabled(false); // Disable the apply button.
				} else { // If the text field is empty, print an error message.
					System.err.println("Unable to apply changes: the text field is empty!");
				}
				Methods.loadLibrary(Paths.get(dirField.getText()));
				Main.refreshFrame();
			}
			
			applied = false;
			
            setVisible(false);
            dispose();
		}
		if (command.equals("Apply")) {
			if (!dirField.getText().isEmpty()) { // If the text field is not empty, continue.
				Methods.filePaths.clear(); // Clear filePaths before repopulating it.
				Methods.dirPath = dirField.getText(); // Set dirPath equal to the text field contents.
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter("lib-dir.txt")); // Create a writer to write to the file lib-dir.txt
					writer.write(Methods.dirPath); // Write the new dirPath to lib-dir.txt
					writer.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				applyButton.setEnabled(false); // Disable the apply button.
			} else { // If the text field is empty, print an error message.
				System.err.println("Unable to apply changes: the text field is empty!");
			}
			Methods.loadLibrary(Paths.get(dirField.getText())); // Reload the library.
			applied = true;
			Main.refreshFrame(); // Refresh main frame.
		}
		if (command.equals("Cancel")) {
			applied = false;
			setVisible(false);
			dispose();
		}
		if (command.equals("...")) {
			Methods.searchDirectory();
		}
	}
}
