package net.sprauer.sitzplaner.view.Commands;

import net.sprauer.sitzplaner.view.ClassRoom;

public class Factory {

	static ClassRoom classView;

	public static CommandNewSeatingPlan CommandNewSeatingPlan;
	public static CommandLoadClass CommandLoadClass;
	public static CommandSaveClass CommandSaveClass;
	public static CommandNewClass CommandNewClass;
	public static CommandLoadSettings CommandLoadSettings;
	public static CommandSaveSettings CommandSaveSettings;
	public static CommandNewSettings CommandNewSettings;
	public static CommandDeleteSettings CommandDeleteSettings;

	public static void init() {
		CommandNewSeatingPlan = new CommandNewSeatingPlan();
		CommandLoadClass = new CommandLoadClass();
		CommandSaveClass = new CommandSaveClass();
		CommandNewClass = new CommandNewClass();
		CommandLoadSettings = new CommandLoadSettings();
		CommandSaveSettings = new CommandSaveSettings();
		CommandNewSettings = new CommandNewSettings();
		CommandDeleteSettings = new CommandDeleteSettings();

	}

}
