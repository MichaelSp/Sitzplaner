package net.sprauer.sitzplaner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelData implements Serializable {

	private static final long serialVersionUID = 3968605175957594037L;
	protected List<Student> students = new ArrayList<Student>();

	public ModelData() {
		super();
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public Student first() {
		return students.get(0);
	}

	public int size() {
		return students.size();
	}

	public List<Student> getStudents() {
		return students;
	}

	public Student get(int i) {
		return students.get(i);
	}

	public void resetPositions() {
		for (Student stud : students) {
			stud.position = null;
		}

	}
}