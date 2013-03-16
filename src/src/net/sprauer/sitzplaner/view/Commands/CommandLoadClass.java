package net.sprauer.sitzplaner.view.Commands;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import net.sprauer.sitzplaner.EA.GeneString;

public class CommandLoadClass extends AbstractCommand {

	private static final long serialVersionUID = 4563613037604731931L;

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedFile = getFile(FileDialog.LOAD);
		if (selectedFile != null) {
			loadFrom(selectedFile);
		}
	}

	public void loadFrom(String selectedFile) {
		try {
			final FileInputStream fo = new FileInputStream(selectedFile);
			final ObjectInputStream ois = new ObjectInputStream(fo);
			Factory.classModel = (GeneString) ois.readObject();
			ois.close();
			Factory.CommandNewSeatingPlan.invoke();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String getButtonCaption() {
		return "Laden";
	}

	@Override
	public String getToolTip() {
		return "Eine Klasse aus einer .cls-Datei laden";
	}
}
