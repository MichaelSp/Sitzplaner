package net.sprauer.sitzplaner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import net.sprauer.sitzplaner.EA.Chromosome;
import net.sprauer.sitzplaner.EA.Configuration;
import net.sprauer.sitzplaner.EA.EAFactory;
import net.sprauer.sitzplaner.EA.Strategy;
import net.sprauer.sitzplaner.view.ClassRoom;

public class Generation {

	private List<Chromosome> population = new Vector<Chromosome>();
	Chromosome bestSolution = null;
	private final Configuration configuration;

	public Generation(Configuration conf) throws Exception {
		this.configuration = conf;
		add(EAFactory.greedy(conf));
	}

	public Chromosome getBestSolution() {
		return bestSolution;
	}

	public void clear() {
		population = new Vector<Chromosome>();
		bestSolution = null;
	}

	private void add(List<Chromosome> children) {
		for (Chromosome chromosome : children) {
			add(chromosome);
		}
		if (!ClassRoom.instance().isUpdating() && population.size() % 20 == 0) {
			ClassRoom.instance().showChromosome(bestSolution);
		}
	}

	public void add(Chromosome chrome) {
		population.add(chrome);
		if (bestSolution == null || chrome.getFitness() >= bestSolution.getFitness()) {
			bestSolution = chrome;
		}
	}

	public void evolve() {
		Collections.sort(population);
		List<Chromosome> parents = selecteTheBestAndKillTheRest();

		List<Chromosome> children = new ArrayList<Chromosome>();
		if (configuration.getStrategy() == Strategy.Rank) {
			children.addAll(rankBasedSwaps(parents));
			children.addAll(rankBasedInversions(parents));
		} else {
			for (Chromosome chromosome : parents) {
				children.addAll(chromosome.swap());
				children.addAll(chromosome.invert());
			}
		}
		add(children);
	}

	private List<Chromosome> rankBasedSwaps(List<Chromosome> parents) {
		int round = 1;
		List<Chromosome> children = new ArrayList<Chromosome>();
		while (children.size() < configuration.getDescendantsUsingSwap()) {
			for (int i = 0; i < round && i < parents.size(); i++) {
				children.add(parents.get(i).doOneSwap());
			}
			round++;
		}
		return children;
	}

	private List<Chromosome> rankBasedInversions(List<Chromosome> parents) {
		int round = 1;
		List<Chromosome> children = new ArrayList<Chromosome>();
		while (children.size() < configuration.getDescendantsUsingInversion()) {
			for (int i = 0; i < round && i < parents.size(); i++) {
				children.add(parents.get(i).doOneInvert());
			}
			round++;
		}
		return children;
	}

	private List<Chromosome> selecteTheBestAndKillTheRest() {
		List<Chromosome> parents = new ArrayList<Chromosome>();
		if (configuration.getStrategy() == Strategy.Tournament) {
			tournamentSelection(parents);
		} else {
			int selectionSize = Math.min(population.size(), configuration.getParents());
			parents = population.subList(0, selectionSize);
		}

		clear();
		if (configuration.isStrategiePlus()) {
			add(parents);
		}
		return parents;
	}

	private void tournamentSelection(List<Chromosome> parents) {
		while (parents.size() < configuration.getTournamentSize() && parents.size() < population.size()) {
			int index = (int) (Math.random() * population.size());
			if (!parents.contains(population.get(index))) {
				parents.add(population.get(index));
			}
		}
	}
}