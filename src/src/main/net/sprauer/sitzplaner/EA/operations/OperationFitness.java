package net.sprauer.sitzplaner.EA.operations;

import java.awt.Point;

import net.sprauer.sitzplaner.EA.Chromosome;
import net.sprauer.sitzplaner.EA.EAOperation;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.model.Student;

public class OperationFitness extends EAOperation {

	private Chromosome gene;
	private final int WEIGHT_PRIORITY = 1;
	private final int WEIGHT_RIGHT = 1;
	private final int WEIGHT_RIGHT_RIGHT = 1;
	private final int WEIGHT_BEHIND = 1;

	@Override
	public void invoke(Chromosome gene) throws Exception {
		this.gene = gene;
		double fitness = 0;
		for (int i = 0; i < gene.size(); i++) {
			fitness += fitnessFor(DataBase.instance().getStudent(i), gene.getPositionOf(i));
		}
		gene.setFitness(fitness);
	}

	private double fitnessFor(Student student, Point position) {
		double fitness = 0;
		fitness += WEIGHT_PRIORITY * getPriorityValueOf(student, position.y);
		fitness += getRelationsFor(student, position);
		return fitness;
	}

	private double getRelationsFor(Student student, Point position) {
		double fitness = 0;
		fitness += WEIGHT_RIGHT * relationToRight(student, position);
		fitness += WEIGHT_RIGHT_RIGHT * student.relationTo(gene.studentAt(rightOf(rightOf(position))));
		fitness += WEIGHT_BEHIND * student.relationTo(gene.studentAt(under(position)));
		return fitness;
	}

	private int relationToRight(Student student, Point currentStudentsPosition) {
		return student.relationTo(gene.studentAt(rightOf(currentStudentsPosition)));
	}

	private Point under(Point oldPos) {
		Point pos = new Point();
		pos.x = oldPos.x;
		pos.y = oldPos.y + 1;
		return pos;
	}

	private Point rightOf(Point oldPos) {
		Point pos = new Point();
		pos.x = oldPos.x + 1;
		pos.y = oldPos.y;
		return pos;
	}

	private double getPriorityValueOf(Student student, int positionY) {
		int distanceToBlackboard = positionY;
		return student.getFirstRowPriority() * distanceToBlackboard;
	}
}
