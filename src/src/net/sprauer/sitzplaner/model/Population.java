package net.sprauer.sitzplaner.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import net.sprauer.sitzplaner.EA.Chromosome;

public class Population {

	private static Population _instance;
	private static List<Chromosome> population = new ArrayList<Chromosome>();

	public static Population instance() {
		if (_instance == null) {
			_instance = new Population();
		}
		return _instance;
	}

	public void insert(int i, Point randomPosition) {
		// TODO Auto-generated method stub

	}

	public Chromosome newChildOf(Chromosome parent) throws OperationNotSupportedException {
		Chromosome c = null;
		if (parent == null) {
			c = new Chromosome();
		} else {
			throw new OperationNotSupportedException("Unimplemented");
		}
		return c;
	}

	public Chromosome getBestSolution() {
		if (population.isEmpty()) {
			return null;
		}
		Collections.sort(population);
		return population.get(0);
	}

	public void clear() {
		population = new ArrayList<Chromosome>();
	}

	public void add(Chromosome chrome) {
		population.add(chrome);
	}

	private Population() {
	}
}
