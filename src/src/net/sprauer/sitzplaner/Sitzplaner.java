package net.sprauer.sitzplaner;

import javax.swing.SwingUtilities;

import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.model.Student;
import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.Commands.CommandFactory;

public class Sitzplaner {

	private final GeneString classModel;
	private final ClassRoom classView;
	private final MainWin mainWin;

	public Sitzplaner() {

		mainWin = new MainWin();
		classView = new ClassRoom();
		classModel = new GeneString();
		for (int i = 0; i < 21; i++) {
			classModel.addStudent(new Student((char) (i + 'A') + "", "" + (char) (i + 'A')));
		}
		CommandFactory.init(classModel, classView);

		mainWin.initView(classView);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Sitzplaner();
			}
		});
	}

}
