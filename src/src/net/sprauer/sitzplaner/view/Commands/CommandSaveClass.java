package net.sprauer.sitzplaner.view.Commands;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.view.ClassRoom;

public class CommandSaveClass extends AbstractCommand {

	private static final long serialVersionUID = 7352333440353114802L;

	public CommandSaveClass(ClassRoom classView, GeneString classModel) {
		super(classView, classModel);
		putValue(NAME, "Speichern");
		putValue(SHORT_DESCRIPTION, "Eine Klasse speichern");
	}

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
			oos.writeObject(classModel);
			CommandFactory.CommandNewSeatingPlan.actionPerformed(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
