package net.sprauer.sitzplaner.view.Commands;

import java.awt.event.ActionEvent;

import net.sprauer.sitzplaner.EA.ConfigManager;

public class CommandDeleteSettings extends AbstractCommand {

	private static final long serialVersionUID = -4791420308093557780L;

	@Override
	public void actionPerformed(ActionEvent e) {
		ConfigManager.instance().deleteCurrentSetting();
	}

	@Override
	public String getButtonCaption() {
		return "Delete";
	}

	@Override
	public String getToolTip() {
		return "Deletes the selected configuration from the list.";
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}