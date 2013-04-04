package net.sprauer.sitzplaner.model;

import java.util.Vector;

import net.sprauer.sitzplaner.EA.Chromosome;

public class Generation {

	private Vector<Chromosome> population = new Vector<Chromosome>();
	Chromosome bestSolution = null;

	public Generation(Chromosome adam, int size) {
		add(adam);
		for (int i = 0; i < size; i++) {
			add(newChildOf(adam));
		}
	}

	public Chromosome newChildOf(Chromosome parent) {
		Chromosome c = null;
		if (parent == null) {
			c = new Chromosome();
		} else {
			return parent.swap(DataBase.instance().getSize() / 4);
		}
		return c;
	}

	private void dump() {
		System.out.println("============================ " + getBestSolution().getFitness());
		for (Chromosome ch : population) {
			System.out.println(ch.getFitness());
		}
	}

	public Chromosome getBestSolution() {
		return bestSolution;
	}

	public void clear() {
		population = new Vector<Chromosome>();
		bestSolution = null;
	}

	public void add(Chromosome chrome) {
		population.add(chrome);
		if (bestSolution == null || chrome.getFitness() > bestSolution.getFitness()) {
			bestSolution = chrome;
		}
	}

}