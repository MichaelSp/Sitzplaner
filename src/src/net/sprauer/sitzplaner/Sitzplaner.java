package net.sprauer.sitzplaner;

import javax.swing.SwingUtilities;

import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.Commands.Factory;

public class Sitzplaner {

	private final GeneString classModel;
	private final ClassRoom classView;
	private final MainWin mainWin;

	public Sitzplaner(String defaultFile) {

		mainWin = new MainWin();
		classView = new ClassRoom();
		classModel = new GeneString();
		Factory.init(classModel, classView);

		Factory.CommandLoadClass.loadFrom(defaultFile);

		mainWin.initView(classView);
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
