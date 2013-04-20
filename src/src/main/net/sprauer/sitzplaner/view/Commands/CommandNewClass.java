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
		return "Neue Klasse anlegen";
	}

	@Override
	public String getToolTip() {
		return "Erzeugt eine neue Klasse mit der angegebenen Größe";
	}

}
