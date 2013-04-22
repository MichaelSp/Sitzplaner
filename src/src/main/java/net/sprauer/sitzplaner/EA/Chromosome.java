package net.sprauer.sitzplaner.EA;

import java.awt.Point;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.Vector;

import net.sprauer.sitzplaner.model.DataBase;

public class Chromosome implements Iterable<Point>, Comparator<Chromosome>, Serializable, Cloneable {

	private static final long serialVersionUID = -3578751251444286705L;
	private double fitness = -1;
	private Vector<Point> positions = new Vector<Point>();
	private HashMap<Point, Integer> positionMap = new HashMap<Point, Integer>();
	private boolean calculating;
	public String id = UUID.randomUUID().toString();

	public Chromosome() {
		positions.setSize(DataBase.instance().getSize());
	}

	public int size() {
		return positions.size();
	}

	public double getFitness() {
		if (fitness < 0 && !calculating) {
			try {
				calculating = true;
				fitness = EAFactory.fitness(this);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				calculating = false;
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
	public int compare(Chromosome o1, Chromosome o2) {
		return Double.compare(o1.fitness, o2.fitness);
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

	@Override
	public String toString() {
		return id + "(" + fitness + ")";
	}

	public Chromosome swap(int times) {
		Chromosome chrome = doClone();
		chrome.fitness = -1;
		chrome.doSwap(times);
		return chrome;
	}

	private void doSwap(int times) {
		for (int y = 0; y < times; y++) {
			int i = (int) (Math.random() * positions.size());
			int j = (int) (Math.random() * positions.size());
			positionMap.put(positions.get(i), j);
			positionMap.put(positions.get(j), i);
			Collections.swap(positions, i, j);
		}
	}

	@SuppressWarnings("unchecked")
	private Chromosome doClone() {
		Chromosome chrome;
		try {
			chrome = (Chromosome) this.clone();
			chrome.positionMap = (HashMap<Point, Integer>) positionMap.clone();
			chrome.positions = (Vector<Point>) positions.clone();
			chrome.id = UUID.randomUUID().toString();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return new Chromosome();
		}
		return chrome;
	}
}
