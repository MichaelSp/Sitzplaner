package net.sprauer.sitzplaner.EA;

import java.awt.Color;
import java.io.Serializable;

public class Configuration implements Serializable, Cloneable {

	private static final long serialVersionUID = 5167515545796099132L;
	private boolean strategiePlus = true;
	private Strategy strategy = Strategy.Normal;
	private int tournamentSize = 0;
	private int parents = 10;

	private int childrenUsingInversion = 10;
	private int childrenUsingSwap = 10;
	private int numberOfInversions = 1;
	private int numberOfSwaps = 5;

	private int weightingRight = 5;
	private int weightingRight2 = 1;
	private int weightingBottom = 1;
	private int weightingDiagonal = 1;
	private int weightingPriority = 5;

	private Color color = Color.blue;

	@Override
	public String toString() {
		String ret = strategiePlus ? "+" : ",";
		ret = " [" + parents + ret + getNumberOfDescendents() + "]" + strategy.toString();
		ret += " I=" + childrenUsingInversion + "; S=" + childrenUsingSwap;
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
				if (!ConfigManager.instance().containsColor(col)) {
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
	}

	public int getParents() {
		return parents;
	}

	public void setParents(int parents) {
		this.parents = parents;
		ConfigManager.instance().currentConfigUpdated();
	}

	public int getDescendantsUsingInversion() {
		return childrenUsingInversion;
	}

	public void setDescendentsUsingInversion(int numberOfInversions) {
		this.childrenUsingInversion = numberOfInversions;
	}

	public int getDescendantsUsingSwap() {
		return childrenUsingSwap;
	}

	public void setDescendantsUsingSwap(int numberOfSwaps) {
		this.childrenUsingSwap = numberOfSwaps;
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
	}

	public int getNumberOfDescendents() {
		final int num = childrenUsingInversion + childrenUsingSwap;
		if (getStrategy() == Strategy.Tournament) {
			return num;
		} else {
			return (num * parents);
		}
	}

	public int getWeightingPriority() {
		return weightingPriority;
	}

	public void setWeightingPriority(int weightingPriority) {
		this.weightingPriority = weightingPriority;
	}

	public int getNumberOfInversions() {
		return numberOfInversions;
	}

	public void setNumberOfInversions(int inversions) {
		numberOfInversions = inversions;
	}

	public int getNumberOfSwaps() {
		return numberOfSwaps;
	}

	public void setNumberOfSwaps(int swaps) {
		numberOfSwaps = swaps;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;

	}

	public int getTournamentSize() {
		return tournamentSize;
	}

	public void setTournamentSize(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}

}
