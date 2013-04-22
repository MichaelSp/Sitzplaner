package net.sprauer.sitzplaner.view.Commands;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.SwingUtilities;

import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.panels.ToolsPanel;

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
			resetToNewClassSize(0);
			final FileInputStream fo = new FileInputStream(selectedFile);
			final ObjectInputStream ois = new ObjectInputStream(fo);
			Dimension dim = (Dimension) ois.readObject();
			DataBase.instance().restoreFrom(ois);
			ClassRoom.instance().setNewDimensions(dim);
			ToolsPanel.instance().setClassRoomDimensions(dim, (short) DataBase.instance().getSize());
			ois.close();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Factory.CommandNewSeatingPlan.invoke();
				}
			});
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
		return "Eine Klasse aus einer .cls-Datei laden";
	}
}
