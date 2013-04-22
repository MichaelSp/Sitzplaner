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
		if (parent == null) {
			return new Chromosome();
		} else {
			return parent.swap(DataBase.instance().getSize() / 4);
		}
	}

	private void dump() {
		System.out.println("============================ " + getBestSolution().getFitness());
		for (Chromosome ch : population) {
			System.out.println(ch);
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
		if (bestSolution == null || chrome.getFitness() >= bestSolution.getFitness()) {
			bestSolution = chrome;
		}
	}

}