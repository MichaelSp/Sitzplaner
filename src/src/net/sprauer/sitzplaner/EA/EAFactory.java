package net.sprauer.sitzplaner.EA;

import net.sprauer.sitzplaner.EA.operations.OperationFitness;
import net.sprauer.sitzplaner.EA.operations.OperationGreedy;
import net.sprauer.sitzplaner.exceptions.ClassRoomToSmallException;
import net.sprauer.sitzplaner.view.panels.StatisticsPanel;

public class EAFactory {

	public static OperationGreedy greedy = new OperationGreedy();

	public static void greedy() throws Exception {
		new OperationGreedy().invoke(null);
	}

	public static double fitness(Chromosome gene) throws Exception {
		new OperationFitness().invoke(gene);
		StatisticsPanel.instance().addFitness(gene.getFitness());
		return gene.getFitness();
	}

	public static void swap(Chromosome gene) {
	}

	public static void optimize() throws ClassRoomToSmallException {
	}
}
