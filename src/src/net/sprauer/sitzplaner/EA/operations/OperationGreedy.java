package net.sprauer.sitzplaner.EA.operations;

import java.awt.Point;
import java.util.List;

import net.sprauer.sitzplaner.EA.Chromosome;
import net.sprauer.sitzplaner.EA.EAOperation;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.model.Population;
import net.sprauer.sitzplaner.view.helper.Parameter;

public class OperationGreedy extends EAOperation {

	@Override
	public void invoke(Chromosome gene) throws Exception {
		Population pop = Population.instance();
		Chromosome chrome = pop.newChildOf(null);
		List<Point> randomUniquePositions = Parameter.randomUniquePositions(DataBase.size());
		for (int i = 0; i < DataBase.size(); i++) {
			chrome.setPositionOf(i, randomUniquePositions.remove(randomUniquePositions.size() - 1));
		}
		pop.add(chrome);

	}
	/*
	 * @Override public void invoke(Chromosome gene) throws
	 * ClassRoomToSmallException, ClassNotLoadedException { model = gene;
	 * model.resetPositions(); checkClassRoomSize(); loadAndSortStudents();
	 * 
	 * Student studentB4 = null; do { Point nextFreePosition =
	 * gene.freePositionNextTo(studentB4); Student firstStudent =
	 * getStudentWithLowestPriority();
	 * 
	 * removeFromListOfStudents(firstStudent); firstStudent.position =
	 * nextFreePosition; studentB4 = firstStudent;
	 * System.out.println(listOfStudents); } while (!listOfStudents.isEmpty());
	 * gene = model; }
	 * 
	 * private void removeFromListOfStudents(Student choice) {
	 * listOfStudents.remove(model.getStudents().indexOf(choice)); }
	 * 
	 * private void checkClassRoomSize() throws ClassRoomToSmallException,
	 * ClassNotLoadedException { if (model.size() > (Parameter.numRows *
	 * Parameter.numCols)) { throw new
	 * ClassRoomToSmallException("Der Raum ist zu klein für diese Klasse"); }
	 * else if (model.size() <= 0) { throw new
	 * ClassNotLoadedException("Keine Klasse geladen"); } }
	 * 
	 * private void loadAndSortStudents() { listOfStudents = new
	 * ArrayList<Integer>(); Collections.sort(model.getStudents(), new
	 * Comparator<Student>() {
	 * 
	 * @Override public int compare(Student o1, Student o2) { return
	 * Double.compare(o1.getFirstRowPriority(), o2.getFirstRowPriority()); }
	 * 
	 * }); for (int i = 0; i < model.size(); i++) { listOfStudents.add(i); } }
	 * 
	 * private Student getStudentWithLowestPriority() { return
	 * model.get(listOfStudents.size() - 1); }
	 */
}
