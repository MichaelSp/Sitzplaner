package net.sprauer.sitzplaner.view.Commands;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;

public class CommandLoadSettings extends AbstractCommand {

	private static final long serialVersionUID = 5043688287628913659L;

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedFile = getFile(FileDialog.LOAD, "Load Settings", ".set");
		if (selectedFile != null) {
			loadFrom(selectedFile);
		}
	}

	private void loadFrom(String selectedFile) {

	}

	@Override
	public String getButtonCaption() {
		return "Load...";
	}

	@Override
	public String getToolTip() {
		return "Loads a configuration from the selected file.";
	}
}