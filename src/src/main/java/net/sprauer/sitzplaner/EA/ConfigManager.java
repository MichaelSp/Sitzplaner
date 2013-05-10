package net.sprauer.sitzplaner.EA;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractListModel;

import net.sprauer.sitzplaner.view.panels.ToolsPanel;

public class ConfigManager extends AbstractListModel implements Iterable<Configuration> {

	private static final long serialVersionUID = -8316518150648526395L;
	private static ConfigManager _instance;
	private final Vector<Configuration> parameters = new Vector<Configuration>();
	private int currentIndex = 0;
	private boolean blockUpdates;
	private final boolean configurationChanged = false;

	private ConfigManager() {
		parameters.add(new Configuration());
		blockUpdates = false;
	}

	public static ConfigManager instance() {
		if (_instance == null) {
			_instance = new ConfigManager();
		}
		return _instance;
	}

	@Override
	public Object getElementAt(int index) {
		return parameters.get(index);
	}

	@Override
	public int getSize() {
		return parameters.size();
	}

	public Configuration getCurrentConfig() {
		return parameters.get(currentIndex);
	}

	public void duplicateCurrent() {
		parameters.insertElementAt((Configuration) getCurrentConfig().clone(), currentIndex);
		if (getSize() >= Configuration.colors().length) {
			ToolsPanel.instance().setButtonNewConfigEnabled(false);
		}
		ToolsPanel.instance().setButtonDeleteConfigEnabled(true);
		fireIntervalAdded(this, currentIndex + 1, currentIndex + 1);
	}

	public void setCurrentConfigIndex(int index) {
		if (index == -1) {
			ToolsPanel.instance().selectConfiguration(Math.max(0, currentIndex - 1));
			return;
		}
		if (index == currentIndex) {
			return;
		}
		currentIndex = index;
		currentConfigUpdated();
	}

	@Override
	public Iterator<Configuration> iterator() {
		return parameters.iterator();
	}

	public void currentConfigUpdated() {
		if (blockUpdates) {
			return;
		}
		blockUpdates = true;
		fireContentsChanged(this, currentIndex, currentIndex);
		blockUpdates = false;
	}

	public void deleteCurrentSetting() {
		parameters.remove(currentIndex);
		fireIntervalRemoved(this, currentIndex, currentIndex);
		if (currentIndex == 0) {
			// ToolsPanel.instance().selectConfiguration(currentIndex);
		}
		if (parameters.size() <= 1) {
			ToolsPanel.instance().setButtonDeleteConfigEnabled(false);
		}
		ToolsPanel.instance().setButtonNewConfigEnabled(true);
	}

}