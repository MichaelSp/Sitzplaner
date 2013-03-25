package net.sprauer.sitzplaner.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class DataBase implements Serializable {

	private static final long serialVersionUID = 3968605175957594037L;
	private static Vector<Student> students = new Vector<Student>();

	public static void addStudent(Student student) {
		students.add(student);
	}

	public static Student first() {
		return students.get(0);
	}

	public static int size() {
		return students.size();
	}

	public Student get(int i) {
		return students.get(i);
	}

	public static void setName(int index, String firstName, String lastName) {
		students.get(index).setName(firstName, lastName);
	}

	public static String getName(int index) {
		return students.get(index).toString();
	}

	public static Student getStudent(int index) {
		return students.get(index);
	}

	public static void init(int classSize) {
		students.clear();
		for (int i = 0; i < classSize; i++) {
			addStudent(new Student((char) (i + 'A') + "", "" + (char) (i + 'A')));
		}
	}

	public static int relationBetween(int source, int target) {
		return students.get(source).relationTo(target);
	}

	public static int getPriority(int studentIndex) {
		return students.get(studentIndex).getFirstRowPriority();
	}

	public static void setPriority(int student, int prio) {
		students.get(student).setFirstRowFactor(prio);
	}

	public static void setRelationBetween(int source, int target, int relation) {
		students.get(source).setRelationTo(target, relation);
	}

	private DataBase() {
	}

	@SuppressWarnings("unchecked")
	public static void restoreFrom(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		students = (Vector<Student>) ois.readObject();
	}

	public static void saveTo(ObjectOutputStream oos) throws IOException {
		oos.writeObject(students);
	}
}