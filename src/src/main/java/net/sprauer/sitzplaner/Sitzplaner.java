package net.sprauer.sitzplaner;

import java.io.File;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.sprauer.sitzplaner.view.MainWin;
import net.sprauer.sitzplaner.view.Commands.Factory;

public class Sitzplaner {

	private final MainWin mainWin;

	public Sitzplaner(String defaultConfigurationFile, String defaultClassFile) {

		setStyle();

		mainWin = new MainWin();
		Factory.init();
		mainWin.initView();

		if (fileExists(defaultClassFile)) {
			Factory.CommandLoadClass.loadFrom(defaultClassFile);
		} else {
			System.err.println("Unable to load: " + defaultClassFile);
		}
		if (fileExists(defaultConfigurationFile)) {
			Factory.CommandLoadSettings.loadFrom(defaultConfigurationFile);
		} else {
			System.err.println("Unable to load: " + defaultConfigurationFile);
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
				final String configFile = (args.length >= 1) ? args[0] : "DEFAULT.conf";
				final String classFile = (args.length >= 2) ? args[1] : "DEFAULT.cls";
				new Sitzplaner(configFile, classFile);
			}
		});
	}

}
