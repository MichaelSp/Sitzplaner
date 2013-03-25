package net.sprauer.sitzplaner.EA.operations;

import java.awt.Point;

import net.sprauer.sitzplaner.EA.Chromosome;
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
			fitness += fitnessFor(DataBase.getStudent(i), gene.getPositionOf(i));
		}
		gene.setFitness(fitness);
	}

	private double fitnessFor(Student student, Point position) {
		double fitness = 0;
		fitness += getPriorityValueOf(student, position.y);
		fitness += getRelationsFor(student, position);
		return fitness;
	}

	private double getRelationsFor(Student student, Point position) {
		double fitness = 0;
		fitness += relationToRight(student, position) * 1;
		fitness += student.relationTo(gene.studentAt(rightOf(rightOf(position))));
		fitness += student.relationTo(gene.studentAt(under(position)));
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
	/*
	 * @Override public void invoke(Chromosome gene) throws Exception { double
	 * fitness = 0; for (int x = 0; x < Parameter.numCols; x++) { for (int y =
	 * 0; y < Parameter.numRows; y++) { Student student =
	 * gene.studentAtPosition(new Point(x, y)); fitness +=
	 * student.getFirstRowPriority(); fitness +=
	 * student.relationTo(rightOf(gene, student)); fitness +=
	 * student.relationTo(rightOf(gene, rightOf(gene, student))); fitness +=
	 * student.relationTo(below(gene, student)); fitness +=
	 * student.relationTo(below(gene, below(gene, student))); if
	 * (blowAndRightOf) { fitness += student.relationTo(below(gene,
	 * rightOf(gene, student))); } } } System.out.println("fitness: " +
	 * fitness); }
	 * 
	 * private Student below(Chromosome gene, Student student) { return
	 * gene.studentAtPosition(gene.below(student)); }
	 * 
	 * private Student rightOf(Chromosome gene, Student student) { return
	 * gene.studentAtPosition(gene.rightOf(student)); }
	 */
}
