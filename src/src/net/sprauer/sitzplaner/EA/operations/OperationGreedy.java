package net.sprauer.sitzplaner.EA.operations;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import net.sprauer.sitzplaner.EA.Chromosome;
import net.sprauer.sitzplaner.EA.EAOperation;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.model.Population;
import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.helper.Parameter;

public class OperationGreedy extends EAOperation {

	private Chromosome chrome;
	private final Vector<Integer> studentsByPriority = new Vector<Integer>();

	@Override
	public void invoke(Chromosome gene) throws Exception {
		Population pop = Population.instance();
		chrome = pop.newChildOf(null);

		fillAndSortStudents();

		int x = 0;
		int y = Parameter.numRows - 1;
		while (!studentsByPriority.isEmpty()) {
			int student = getNextStudentByPriority();
			chrome.setPositionOf(student, new Point(x, y));
			x += 1;
			if (x >= ClassRoom.instance().getDimensions().getWidth()) {
				x = 0;
				y -= 1;
			}
		}

		pop.add(chrome);
	}

	private void fillAndSortStudents() {
		for (int i = 0; i < DataBase.size(); i++) {
			studentsByPriority.add(i);
		}
		Collections.sort(studentsByPriority, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return DataBase.getPriority(o2) - DataBase.getPriority(o1);
			}
		});
	}

	private int getNextStudentByPriority() {
		return studentsByPriority.remove(0);
	}
}
