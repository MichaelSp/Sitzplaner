package net.sprauer.sitzplaner.EA;

import net.sprauer.sitzplaner.EA.operations.OperationFitness;
import net.sprauer.sitzplaner.EA.operations.OperationGreedy;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.model.Generation;
import net.sprauer.sitzplaner.view.panels.StatisticsPanel;

public class EAFactory {

	private static int populationSize = 1;
	private static int numOfGenerations = 1;
	static Chromosome currentGeneration;

	static double fitness(Chromosome gene) throws Exception {
		new OperationFitness().invoke(gene);
		return gene.getFitness();
	}

	public static Chromosome greedy() throws Exception {
		Chromosome chrome = new Chromosome();
		new OperationGreedy().invoke(chrome);
		return chrome;
	}

	public static Chromosome nextGeneration() throws Exception {
		if (currentGeneration == null || currentGeneration.size() != DataBase.instance().getSize()) {
			currentGeneration = greedy();
		}
		Generation generation = new Generation(currentGeneration, getPopulationSize());
		currentGeneration = generation.getBestSolution();
		StatisticsPanel.instance().addFitness(currentGeneration.getFitness());
		return currentGeneration;
	}

	public static Chromosome nextGenerations() throws Exception {
		for (int i = 0; i < numOfGenerations; i++) {
			nextGeneration();
		}
		return currentGeneration;
	}

	public static void resetGeneration() {
		currentGeneration = null;
	}

	public static int getPopulationSize() {
		return populationSize;
	}

	public static void setPopulationSize(int populationSize) {
		EAFactory.populationSize = populationSize;
	}

	public static void setNumberOfGenerations(int numOfGenerations) {
		EAFactory.numOfGenerations = numOfGenerations;

	}

}
