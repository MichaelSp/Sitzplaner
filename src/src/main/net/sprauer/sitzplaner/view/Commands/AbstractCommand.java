package net.sprauer.sitzplaner.view.Commands;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.sprauer.sitzplaner.EA.EAFactory;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.MainWin;
import net.sprauer.sitzplaner.view.panels.StatisticsPanel;

public abstract class AbstractCommand extends AbstractAction {

	private static final long serialVersionUID = 8867316848013850813L;

	public abstract String getButtonCaption();

	public abstract String getToolTip();

	public AbstractCommand() {
		putValue(NAME, getButtonCaption());
		putValue(SHORT_DESCRIPTION, getToolTip());
	}

	public void warningBox(String infoMessage, String location) {
		JOptionPane.showMessageDialog(null, infoMessage, "Warning: " + location, JOptionPane.WARNING_MESSAGE);
	}

	protected MainWin getMainWin() {
		Component parent = ClassRoom.instance().getParent();
		while (!(parent instanceof MainWin) && parent != null) {
			parent = parent.getParent();
		}
		return (MainWin) parent;
	}

	protected String getFile(int mode) {
		Frame parent = getMainWin();
		FileDialog fd = new FileDialog(parent, "Klasse speichern");
		fd.setMode(mode);
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".cls");
			}
		});
		fd.setVisible(true);
		String file = fd.getFile();
		if (file != null) {
			file = fd.getDirectory() + file;
		}
		return file;
	}

	public void invoke() {
		actionPerformed(null);
	}

	protected void resetToNewClassSize(int classSize) {
		ClassRoom.instance().clear();
		DataBase.instance().clear();
		DataBase.instance().setSize(classSize);
		EAFactory.resetGeneration();
		StatisticsPanel.instance().clear();
	}
}
