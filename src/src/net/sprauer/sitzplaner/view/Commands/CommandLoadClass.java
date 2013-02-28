package net.sprauer.sitzplaner.view.Commands;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.view.ClassRoom;

public class CommandLoadClass extends AbstractCommand {

	public CommandLoadClass(ClassRoom classView, GeneString classModel) {
		super(classView, classModel);
		putValue(NAME, "Laden");
		putValue(SHORT_DESCRIPTION, "Eine Klasse laden");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedFile = getFile(FileDialog.LOAD);
		if (selectedFile != null) {
			loadFrom(selectedFile);
		}
	}

	void loadFrom(String selectedFile) {
		try {
			final FileInputStream fo = new FileInputStream(selectedFile);
			final ObjectInputStream oos = new ObjectInputStream(fo);
			classModel = (GeneString) oos.readObject();
			CommandFactory.CommandNewSeatingPlan.invoke();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
