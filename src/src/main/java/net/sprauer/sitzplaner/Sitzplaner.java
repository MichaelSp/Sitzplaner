package net.sprauer.sitzplaner;

import java.io.File;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.sprauer.sitzplaner.view.MainWin;
import net.sprauer.sitzplaner.view.Commands.Factory;

public class Sitzplaner {

	private final MainWin mainWin;

	public Sitzplaner(String defaultFile) {

		setStyle();

		mainWin = new MainWin();
		Factory.init();
		mainWin.initView();

		if (fileExists(defaultFile)) {
			Factory.CommandLoadClass.loadFrom(defaultFile);
		} else {
			System.err.println("Unable to load: " + defaultFile);
		}
	}

	private void setStyle() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}

	private boolean fileExists(String defaultFile) {
		File f = new File(defaultFile);
		return f.exists() && f.isFile();
	}

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Sitzplaner((args.length >= 1) ? args[0] : "DEFAULT.cls");
			}
		});
	}

}
