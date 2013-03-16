package net.sprauer.sitzplaner.EA.operations;

import java.awt.Point;

import net.sprauer.sitzplaner.EA.EAOperation;
import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.model.Student;
import net.sprauer.sitzplaner.view.Parameter;

public class OperationFitness implements EAOperation {

	static boolean blowAndRightOf = false;

	@Override
	public void invoke(GeneString gene) throws Exception {
		double fitness = 0;

		for (int x = 0; x < Parameter.numCols; x++) {
			for (int y = 0; y < Parameter.numRows; y++) {
				Student student = gene.studentAtPosition(new Point(x, y));
				fitness += student.getFirstRowFactor();
				fitness += student.relationTo(rightOf(gene, student));
				fitness += student.relationTo(rightOf(gene, rightOf(gene, student)));
				fitness += student.relationTo(below(gene, student));
				fitness += student.relationTo(below(gene, below(gene, student)));
				if (blowAndRightOf) {
					fitness += student.relationTo(below(gene, rightOf(gene, student)));
				}
			}
		}
		System.out.println("fitness: " + fitness);
	}

	private Student below(GeneString gene, Student student) {
		return gene.studentAtPosition(gene.below(student));
	}

	private Student rightOf(GeneString gene, Student student) {
		return gene.studentAtPosition(gene.rightOf(student));
	}
}
