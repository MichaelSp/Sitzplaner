package net.sprauer.sitzplaner.view.Commands;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.sprauer.sitzplaner.MainWin;
import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.view.ClassRoom;

public abstract class AbstractCommand extends AbstractAction {

	private static final long serialVersionUID = 8867316848013850813L;
	protected final ClassRoom classView;
	protected GeneString classModel;

	public AbstractCommand(ClassRoom classView, GeneString classModel) {
		this.classView = classView;
		this.classModel = classModel;
	}

	public void warningBox(String infoMessage, String location) {
		JOptionPane.showMessageDialog(null, infoMessage, "Warning: " + location, JOptionPane.WARNING_MESSAGE);
	}

	protected MainWin getMainWin() {
		Component parent = classView.getParent();
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
}
