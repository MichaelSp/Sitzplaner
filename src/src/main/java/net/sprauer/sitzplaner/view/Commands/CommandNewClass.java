package net.sprauer.sitzplaner.view.Commands;

import java.awt.event.ActionEvent;

import net.sprauer.sitzplaner.view.panels.ToolsPanel;

public class CommandNewClass extends AbstractCommand {

	private static final long serialVersionUID = 757556993872980074L;

	@Override
	public void actionPerformed(ActionEvent e) {
		int classSize = ToolsPanel.instance().getClassSize();
		resetToNewClassSize(classSize);
		Factory.CommandNewSeatingPlan.invoke();
	}

	@Override
	public String getButtonCaption() {
		return "Create new class";
	}

	@Override
	public String getToolTip() {
		return "Creates a new class with the given size and dimensions";
	}

}
