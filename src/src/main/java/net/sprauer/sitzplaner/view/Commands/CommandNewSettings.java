package net.sprauer.sitzplaner.view.Commands;

import java.awt.event.ActionEvent;

import net.sprauer.sitzplaner.EA.ConfigManager;

public class CommandNewSettings extends AbstractCommand {

	private static final long serialVersionUID = -5703330943667473516L;

	@Override
	public void actionPerformed(ActionEvent e) {
		ConfigManager.instance().duplicateCurrent();
	}

	@Override
	public String getButtonCaption() {
		return "New";
	}

	@Override
	public String getToolTip() {
		return "Create a new configuration";
	}
}