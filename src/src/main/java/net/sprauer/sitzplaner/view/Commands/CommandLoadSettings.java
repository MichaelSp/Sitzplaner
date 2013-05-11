package net.sprauer.sitzplaner.view.Commands;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import net.sprauer.sitzplaner.EA.ConfigManager;

public class CommandLoadSettings extends AbstractCommand {

	private static final long serialVersionUID = 5043688287628913659L;

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedFile = getFile(FileDialog.LOAD, "Load configuration", ".conf");
		if (selectedFile != null) {
			loadFrom(selectedFile);
		}
	}

	public void loadFrom(String selectedFile) {
		try {
			final FileInputStream fo = new FileInputStream(selectedFile);
			final ObjectInputStream ois = new ObjectInputStream(fo);
			if (!ConfigManager.instance().loadConfigFrom(ois)) {
				warningBox("Unable to load the configuration.\nToo many configurations loaded.\n"
						+ "Please delete at least one configuration, than load this configuration again.",
						"Sitzplaner - load configuration");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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