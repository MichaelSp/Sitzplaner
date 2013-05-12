package net.sprauer.sitzplaner.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class DataBase {

	private Vector<Student> students = new Vector<Student>();
	private static DataBase _instance = new DataBase();

	public static DataBase instance() {
		return _instance;
	}

	public int getSize() {
		return students.size();
	}

	public void setName(int index, String name) {
		students.get(index).setName(name);
	}

	public String getName(int index) {
		return students.get(index).toString();
	}

	public Student getStudent(int index) {
		return students.get(index);
	}

	private Student createStudent(int i) {
		return new Student(i);
	}

	public int relationBetween(int source, int target) {
		return students.get(source).relationTo(target);
	}

	public void setRelationBetween(int source, int target, int relation) {
		students.get(source).setRelationTo(target, relation);
	}

	public int getPriority(int studentIndex) {
		return students.get(studentIndex).getFirstRowPriority();
	}

	public void setPriority(int student, int prio) {
		students.get(student).setFirstRowFactor(prio);
	}

	@SuppressWarnings("unchecked")
	public void restoreFrom(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		students = (Vector<Student>) ois.readObject();
	}

	boolean isEmpty() {
		return getSize() <= 0;
	}

	public void saveTo(ObjectOutputStream oos) throws IOException {
		oos.writeObject(students);
	}

	public void setSize(int newSize) {
		while (newSize > getSize()) {
			addStudent(createStudent(getSize()));
		}
		while (newSize < getSize()) {
			removeStudent();
		}
	}

	private void removeStudent() {
		int lastStudent = getSize() - 1;
		for (int i = 0; i < lastStudent; i++) {
			getStudent(i).setRelationTo(lastStudent, null);
		}
		students.remove(lastStudent);
	}

	private void addStudent(Student student) {
		students.add(student);
	}

	private DataBase() {
	}

	public void clear() {
		students.clear();
	}

}