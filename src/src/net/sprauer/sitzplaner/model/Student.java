package net.sprauer.sitzplaner.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student implements Serializable {

	private static final long serialVersionUID = 9104891853035096375L;
	private int index;
	private String firstName;
	private String lastName;
	private int firstRowPriority;
	private final Map<Integer, Integer> relations = new HashMap<Integer, Integer>();

	public Student() {
		firstRowPriority = 1;
	}

	public Student(String fName, String lName) {
		firstName = fName;
		lastName = lName;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	public void setFirstRowFactor(int priority) {
		this.firstRowPriority = priority;
	}

	public int getFirstRowPriority() {
		return firstRowPriority;
	}

	public int relationTo(int index) {
		if (relations.containsKey(index)) {
			return relations.get(index);
		} else {
			return 0;
		}
	}

	public void setName(String fName, String lName) {
		firstName = fName;
		lastName = lName;
	}

	public void setRelationTo(int student, Integer value) {
		relations.put(student, value);
		DataBase.getStudent(student).relations.put(index, value);
	}

	public int getIndex() {
		return index;
	}

}
