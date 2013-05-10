package net.sprauer.sitzplaner.EA;

import java.awt.Color;
import java.io.Serializable;

public class Configuration implements Serializable, Cloneable {

	private static final long serialVersionUID = -4387912538408349150L;

	private boolean strategiePlus = true;
	private int parents = 10;

	private int numberOfInversions = 10;
	private int numberOfSwaps = 10;

	private int weightingRight = 5;
	private int weightingRight2 = 1;
	private int weightingBottom = 1;
	private int weightingDiagonal = 1;
	private int weightingPriority = 5;

	private Color color = Color.blue;

	@Override
	public String toString() {
		String ret = strategiePlus ? "+" : ",";
		ret = " [" + parents + ret + getDescendents() + "] ";
		ret += " I=" + numberOfInversions + "; S=" + numberOfSwaps;
		ret += " W(" + weightingPriority + "," + weightingRight + "," + weightingRight2 + "," + weightingDiagonal + ","
				+ weightingBottom + ")";
		return ret;
	}

	@Override
	public Object clone() {
		try {
			final Configuration set = (Configuration) super.clone();
			for (int i = 0; i < colors().length; i++) {
				Color col = colors()[i];
				if (!containsColor(col)) {
					color = col;
					return set;
				}
			}
			return null;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean containsColor(Color col) {
		for (Configuration conf : ConfigManager.instance()) {
			if (conf.color == col) {
				return true;
			}
		}
		return false;
	}

	public static Color[] colors() {
		Color[] colors = { Color.blue, Color.red, Color.green, Color.yellow, Color.black, Color.pink, Color.cyan, Color.magenta,
				Color.orange, Color.white };
		return colors;
	}

	public boolean isStrategiePlus() {
		return strategiePlus;
	}

	public void setStrategiePlus(boolean strategiePlus) {
		this.strategiePlus = strategiePlus;
		ConfigManager.instance().currentConfigUpdated();
	}

	public int getParents() {
		return parents;
	}

	public void setParents(int parents) {
		this.parents = parents;
		ConfigManager.instance().currentConfigUpdated();
	}

	public int getNumberOfInversions() {
		return numberOfInversions;
	}

	public void setNumberOfInversions(int numberOfInversions) {
		this.numberOfInversions = numberOfInversions;
		ConfigManager.instance().currentConfigUpdated();
	}

	public int getNumberOfSwaps() {
		return numberOfSwaps;
	}

	public void setNumberOfSwaps(int numberOfSwaps) {
		this.numberOfSwaps = numberOfSwaps;
	}

	public int getWeightingRight() {
		return weightingRight;
	}

	public void setWeightingRight(int weightingRight) {
		this.weightingRight = weightingRight;
	}

	public int getWeightingRight2() {
		return weightingRight2;
	}

	public void setWeightingRight2(int weightingRight2) {
		this.weightingRight2 = weightingRight2;
	}

	public int getWeightingBottom() {
		return weightingBottom;
	}

	public void setWeightingBottom(int weightingBottom) {
		this.weightingBottom = weightingBottom;
	}

	public int getWeightingDiagonal() {
		return weightingDiagonal;
	}

	public void setWeightingDiagonal(int weightingDiagonal) {
		this.weightingDiagonal = weightingDiagonal;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		ConfigManager.instance().currentConfigUpdated();
	}

	public int getDescendents() {
		return ((numberOfInversions + numberOfSwaps) * parents);
	}

	public int getWeightingPriority() {
		return weightingPriority;
	}

	public void setWeightingPriority(int weightingPriority) {
		this.weightingPriority = weightingPriority;
	}
}