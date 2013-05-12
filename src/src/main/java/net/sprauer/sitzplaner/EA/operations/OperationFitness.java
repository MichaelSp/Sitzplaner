package net.sprauer.sitzplaner.EA.operations;

import java.awt.Point;

import net.sprauer.sitzplaner.EA.Chromosome;
import net.sprauer.sitzplaner.EA.Configuration;
import net.sprauer.sitzplaner.EA.EAOperation;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.model.Student;

public class OperationFitness extends EAOperation {

	private Chromosome gene;

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
		fitness += gene.getConfig().getWeightingPriority() * getPriorityValueOf(student, position.y);
		fitness += getRelationsFor(student, position);
		return fitness;
	}

	private double getRelationsFor(Student student, Point position) {
		double fitness = 0;
		final Configuration config = gene.getConfig();
		fitness += config.getWeightingRight() * student.relationTo(gene.studentAt(rightOf(position)));
		fitness += config.getWeightingRight2() * student.relationTo(gene.studentAt(rightOf(rightOf(position))));
		fitness += config.getWeightingBottom() * student.relationTo(gene.studentAt(under(position)));
		fitness += config.getWeightingDiagonal() * student.relationTo(gene.studentAt(under(rightOf(position))));
		fitness += config.getWeightingDiagonal() * student.relationTo(gene.studentAt(under(leftOf(position))));
		return fitness;
	}

	private Point under(Point oldPos) {
		Point pos = new Point();
		pos.x = oldPos.x;
		pos.y = oldPos.y + 1;
		return pos;
	}

	private Point leftOf(Point position) {
		Point pos = new Point();
		pos.x = position.x - 1;
		pos.y = position.y;
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
