package net.sprauer.sitzplaner.EA;

import java.awt.Point;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import net.sprauer.sitzplaner.model.DataBase;

public class Chromosome implements Iterable<Point>, Comparable<Chromosome>, Serializable, Cloneable {

	private static final long serialVersionUID = -3578751251444286705L;
	private double fitness = -1;
	private final Vector<Point> positions = new Vector<Point>();
	private final HashMap<Point, Integer> positionMap = new HashMap<Point, Integer>();

	public Chromosome() {
		positions.setSize(DataBase.size());
	}

	public int size() {
		return positions.size();
	}

	public double getFitness() {
		if (fitness < 0) {
			try {
				EAFactory.fitness(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fitness;
	}

	@Override
	public Iterator<Point> iterator() {
		return positions.iterator();
	}

	public Point getPositionOf(int index) {
		return positions.get(index);
	}

	public void setPositionOf(int i, Point newPos) {
		positions.set(i, newPos);
		positionMap.put(newPos, i);
	}

	@Override
	public int compareTo(Chromosome o) {
		return Double.compare(fitness, o.fitness);
	}

	public void setFitness(double f) {
		fitness = f;
	}

	public int studentAt(Point pos) {
		if (positionMap.containsKey(pos)) {
			return positionMap.get(pos);
		} else {
			return -1;
		}
	}

	public Chromosome swap(int times) {
		Chromosome chrome;
		try {
			chrome = (Chromosome) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return new Chromosome();
		}
		chrome.fitness = -1;
		for (int y = 0; y < times; y++) {
			int i = (int) (Math.random() * positions.size());
			int j = (int) (Math.random() * positions.size());
			positionMap.put(positions.get(i), j);
			positionMap.put(positions.get(j), i);
			Collections.swap(positions, i, j);
		}
		return chrome;
	}
}
