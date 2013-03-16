package net.sprauer.sitzplaner.EA;

import net.sprauer.sitzplaner.EA.operations.OperationFitness;
import net.sprauer.sitzplaner.EA.operations.OperationGreedy;
import net.sprauer.sitzplaner.exceptions.ClassNotLoadedException;
import net.sprauer.sitzplaner.exceptions.ClassRoomToSmallException;

public class EAFactory {

	public static OperationGreedy greedy = new OperationGreedy();

	public static void greedy(GeneString gene) throws ClassRoomToSmallException, ClassNotLoadedException {
		new OperationGreedy().invoke(gene);
	}

	public static double fitness(GeneString gene) throws Exception {
		new OperationFitness().invoke(gene);
		return gene.getFitness();
	}

	public static void swap(GeneString gene) {
	}

	public static void optimize(GeneString gene) throws ClassRoomToSmallException {
	}
}
