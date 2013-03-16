package net.sprauer.sitzplaner.view.Commands;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class CommandSaveClass extends AbstractCommand {

	private static final long serialVersionUID = 7352333440353114802L;

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedFile = getFile(FileDialog.SAVE);
		if (selectedFile != null) {
			saveTo(selectedFile);
		}
	}

	void saveTo(String selectedFile) {
		try {
			final FileOutputStream fo = new FileOutputStream(selectedFile);
			final ObjectOutputStream oos = new ObjectOutputStream(fo);
			oos.writeObject(getModel());
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String getButtonCaption() {
		return "Speichern";
	}

	@Override
	public String getToolTip() {
		return "Eine Klasse speichern";
	}

}
