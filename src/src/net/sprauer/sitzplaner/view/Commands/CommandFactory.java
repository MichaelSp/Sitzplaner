package net.sprauer.sitzplaner.view.Commands;

import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.view.ClassRoom;

public class CommandFactory {

	private static GeneString classModel;
	private static ClassRoom classView;

	public static AbstractCommand CommandNewSeatingPlan;
	public static AbstractCommand CommandLoadClass;
	public static AbstractCommand CommandSaveClass;

	public static void init(GeneString classModel, ClassRoom classView) {
		CommandFactory.classModel = classModel;
		CommandFactory.classView = classView;

		CommandNewSeatingPlan = new CommandNewSeatingPlan(classView, classModel);
		CommandLoadClass = new CommandLoadClass(classView, classModel);
		CommandSaveClass = new CommandSaveClass(classView, classModel);
	}

}
