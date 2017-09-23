package mp3lib;

import javax.swing.event.DocumentListener;

public class DocumentEvent implements DocumentListener {
	
	@Override
	public void changedUpdate(javax.swing.event.DocumentEvent arg0) {
		dirFieldChanged();
	}

	@Override
	public void insertUpdate(javax.swing.event.DocumentEvent arg0) {
		dirFieldChanged();
	}

	@Override
	public void removeUpdate(javax.swing.event.DocumentEvent arg0) {
		dirFieldChanged();
	}

	
	public void dirFieldChanged() {
		if (PreferenceFrame.dirField.getText().isEmpty()) {
			PreferenceFrame.applyButton.setEnabled(false);
			PreferenceFrame.applyButton.validate();
			PreferenceFrame.applyButton.repaint();
		} else {
			PreferenceFrame.applyButton.setEnabled(true);
			PreferenceFrame.applyButton.validate();
			PreferenceFrame.applyButton.repaint();
		}
	}
	
}
