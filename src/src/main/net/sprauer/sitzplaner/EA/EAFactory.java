package net.sprauer.sitzplaner.EA;

import net.sprauer.sitzplaner.EA.operations.OperationFitness;
import net.sprauer.sitzplaner.EA.operations.OperationGreedy;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.model.Generation;

public class EAFactory {

	private static final int POPULATION_SIZE = 1;
	static Chromosome currentGeneration;

	public static Chromosome greedy() throws Exception {
		Chromosome chrome = new Chromosome();
		new OperationGreedy().invoke(chrome);
		return chrome;
	}

	public static double fitness(Chromosome gene) throws Exception {
		new OperationFitness().invoke(gene);
		return gene.getFitness();
	}

	public static Chromosome nextGeneration() throws Exception {
		if (currentGeneration == null || currentGeneration.size() != DataBase.instance().getSize()) {
			currentGeneration = greedy();
		}
		Generation generation = new Generation(currentGeneration, POPULATION_SIZE);
		currentGeneration = generation.getBestSolution();
		return currentGeneration;
	}

}
