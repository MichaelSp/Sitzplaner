package net.sprauer.sitzplaner.EA;

public class EAFactory {

	public static OperationGreedy greedy = new OperationGreedy();

	public static void greedy(GeneString gene) throws ClassRoomToSmallException {
		new OperationGreedy().invoke(gene);
	}

	public static double fitness(GeneString gene) {
		return -1;
	}

	public static void swap(GeneString gene) {
	}

	public static void optimize(GeneString gene) throws ClassRoomToSmallException {
	}
}
