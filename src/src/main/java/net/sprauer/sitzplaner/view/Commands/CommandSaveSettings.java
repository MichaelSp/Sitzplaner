package net.sprauer.sitzplaner.view.Commands;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import net.sprauer.sitzplaner.EA.ConfigManager;

public class CommandSaveSettings extends AbstractCommand {

	private static final long serialVersionUID = 3003017320608022052L;

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedFile = getFile(FileDialog.SAVE, "Save configuration to", ".conf");
		if (selectedFile != null) {
			saveTo(selectedFile);
		}
	}

	private void saveTo(String selectedFile) {
		try {
			final FileOutputStream fo = new FileOutputStream(selectedFile);
			final ObjectOutputStream oos = new ObjectOutputStream(fo);
			ConfigManager.instance().saveConfigTo(oos);
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String getButtonCaption() {
		return "Save...";
	}

	@Override
	public String getToolTip() {
		return "Save the current selected configuration to a file.";
	}
}