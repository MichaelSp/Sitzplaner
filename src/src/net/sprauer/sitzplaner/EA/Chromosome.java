package net.sprauer.sitzplaner.EA;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.annotation.PostConstruct;

import net.sprauer.sitzplaner.model.DataBase;

public class Chromosome implements Iterable<Point>, Comparable<Chromosome>, Serializable {

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
}
