package net.sprauer.sitzplaner.EA;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractListModel;

import net.sprauer.sitzplaner.view.panels.ToolsPanel;

public class ConfigManager extends AbstractListModel implements Iterable<Configuration> {

	private static final long serialVersionUID = -8316518150648526395L;
	private static ConfigManager _instance;
	private Vector<Configuration> parameters = new Vector<Configuration>();
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
		if (canAddConfig()) {
			ToolsPanel.instance().setButtonNewConfigEnabled(false);
		}
		ToolsPanel.instance().setButtonDeleteConfigEnabled(true);
		fireIntervalAdded(this, currentIndex + 1, currentIndex + 1);
	}

	private boolean canAddConfig() {
		return getSize() >= Configuration.colors().length;
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

		if (parameters.size() <= 1) {
			ToolsPanel.instance().setButtonDeleteConfigEnabled(false);
		}
		ToolsPanel.instance().setButtonNewConfigEnabled(true);
	}

	public void saveConfigTo(ObjectOutputStream oos) throws IOException {
		oos.writeObject(parameters);
	}

	@SuppressWarnings("unchecked")
	public boolean loadConfigFrom(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		parameters.clear();
		parameters = (Vector<Configuration>) ois.readObject();
		currentIndex = 0;
		fireContentsChanged(this, 0, parameters.size());
		return true;
	}

	public boolean containsColor(Color col) {
		for (Configuration conf : this) {
			if (conf.getColor() == col) {
				return true;
			}
		}
		return false;
	}
}