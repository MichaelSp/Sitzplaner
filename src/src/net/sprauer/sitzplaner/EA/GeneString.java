package net.sprauer.sitzplaner.EA;

import java.awt.Point;

import net.sprauer.sitzplaner.model.ModelData;
import net.sprauer.sitzplaner.model.Student;
import net.sprauer.sitzplaner.view.Parameter;

public class GeneString extends ModelData {

	private static final long serialVersionUID = -3578751251384286705L;
	private double fitness = -1;

	public void addStudent(Student student, Point atPosition) {
		student.position = atPosition;
	}

	public Point freePositionNextTo(Student student) {
		if (student == null) {
			return new Point(0, Parameter.numRows - 1);
		}
		if (positionIsFree(rightOf(student))) {
			return rightOf(student);
		} else if (positionIsFree(below(student))) {
			return below(student);
		} else if (positionIsFree(leftOf(student))) {
			return leftOf(student);
		} else if (positionIsFree(above(student))) {
			return above(student);
		} else {
			return null;
		}
	}

	private Point above(Student student) {
		Point p = (Point) student.position.clone();
		p.y -= 1;
		return p;
	}

	private Point below(Student student) {
		Point p = (Point) student.position.clone();
		p.y += 1;
		return p;
	}

	private Point rightOf(Student student) {
		Point p = (Point) student.position.clone();
		p.x += 1;
		return p;
	}

	private Point leftOf(Student student) {
		Point p = (Point) student.position.clone();
		p.x -= 1;
		return p;
	}

	private boolean positionIsFree(Point pos) {
		if (studentAtPosition(pos) != null) {
			return false;
		}
		if (pos.x < 0 || pos.x >= Parameter.numCols) {
			return false;
		}
		if (pos.y < 0 || pos.y >= Parameter.numRows) {
			return false;
		}
		return true;
	}

	private Student studentAtPosition(Point pos) {
		for (Student student : students) {
			if (student.position != null && student.position.equals(pos)) {
				return student;
			}
		}
		return null;
	}

	public double getFitness() {
		return fitness;
	}

	public void calcFitness() {
		if (getFitness() != -1) {
			return;
		}
		fitness = 0;
		for (Student student : students) {
			fitness += student.getFirstRowFactor(student.position.y);
		}
		System.out.println("fitness: " + fitness);
	}
}
