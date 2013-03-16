package net.sprauer.sitzplaner.view.Commands;

import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.view.ClassRoom;

public class Factory {

	static GeneString classModel;
	static ClassRoom classView;

	public static CommandNewSeatingPlan CommandNewSeatingPlan;
	public static CommandLoadClass CommandLoadClass;
	public static CommandSaveClass CommandSaveClass;
	public static CommandNewClass CommandNewClass;

	public static void init(GeneString classModel, ClassRoom classView) {
		Factory.classModel = classModel;
		Factory.classView = classView;

		CommandNewSeatingPlan = new CommandNewSeatingPlan();
		CommandLoadClass = new CommandLoadClass();
		CommandSaveClass = new CommandSaveClass();
		CommandNewClass = new CommandNewClass();
	}

}
