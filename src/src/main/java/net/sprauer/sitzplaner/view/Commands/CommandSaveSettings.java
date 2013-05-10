package net.sprauer.sitzplaner.view.Commands;

import java.awt.event.ActionEvent;

public class CommandSaveSettings extends AbstractCommand {

	private static final long serialVersionUID = 3003017320608022052L;

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public String getButtonCaption() {
		return "Save...";
	}

	@Override
	public String getToolTip() {
		return "Save the current selected configuration to a file.";
	}
}