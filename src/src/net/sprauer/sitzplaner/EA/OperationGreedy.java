package net.sprauer.sitzplaner.EA;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sprauer.sitzplaner.model.ModelData;
import net.sprauer.sitzplaner.model.Student;
import net.sprauer.sitzplaner.view.Parameter;

public class OperationGreedy implements EAOperation {

	private ModelData model;
	private List<Integer> listOfStudents;

	@Override
	public void invoke(GeneString gene) throws ClassRoomToSmallException {
		model = gene;
		model.resetPositions();
		checkClassRoomSize();
		loadAndSortStudents();

		Student lastStudent = null;
		do {
			Point nextFreePosition = gene.freePositionNextTo(lastStudent);
			Student firstStudent = getStudentWithLowestPriority();
			Student secondStudent = findBestMatchTo(lastStudent);
			Student choice = selectBetterChoice(firstStudent, secondStudent, nextFreePosition, lastStudent);

			removeFromListOfStudents(choice);
			choice.position = nextFreePosition;
			lastStudent = choice;
		} while (!listOfStudents.isEmpty());
	}

	private void removeFromListOfStudents(Student choice) {
		listOfStudents.remove(model.getStudents().indexOf(choice));
	}

	private Student selectBetterChoice(Student firstStudent, Student secondStudent, Point position, Student lastStudent) {
		double firstStudentFitness = firstStudent.getFirstRowFactor(position.y);
		double secondStudentFitness = secondStudent.relationTo(lastStudent);
		if (firstStudentFitness < secondStudentFitness) {
			return firstStudent;
		} else {
			return secondStudent;
		}
	}

	private void checkClassRoomSize() throws ClassRoomToSmallException {
		if (model.size() > (Parameter.numRows * Parameter.numCols)) {
			throw new ClassRoomToSmallException();
		}
	}

	private void loadAndSortStudents() {
		listOfStudents = new ArrayList<Integer>();
		Collections.sort(model.getStudents(), new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				return Double.compare(o1.getFirstRowFactor(-1), o2.getFirstRowFactor(-1));
			}

		});
		for (int i = 0; i < model.size(); i++) {
			listOfStudents.add(i);
		}
	}

	private Student getStudentWithLowestPriority() {
		return model.get(listOfStudents.size() - 1);
	}

	private Student findBestMatchTo(Student student) {
		double bestRelation = 1;
		Student bestStud = null;
		List<Student> students = model.getStudents();
		for (int i = 0; i < students.size(); i++) {
			if (!listOfStudents.contains(i)) {
				continue;
			}
			Student stud = students.get(i);
			Double currentRelation = stud.relationTo(student);
			if (currentRelation < bestRelation) {
				bestStud = stud;
				bestRelation = currentRelation;
			}
		}
		return bestStud;
	}

}
