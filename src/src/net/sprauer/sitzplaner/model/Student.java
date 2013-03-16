package net.sprauer.sitzplaner.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sprauer.sitzplaner.view.Parameter;

public class Student implements Serializable {

	private static final long serialVersionUID = 9104891853035096375L;
	private String firstName;
	private String lastName;
	private double firstRowFactor;
	public Point position;
	private final Map<Student, Double> relations = new HashMap<Student, Double>();

	public Student() {
		firstRowFactor = 1.0;
	}

	public Student(String fName, String lName) {
		firstName = fName;
		lastName = lName;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	public void setFirstRowFactor(double priority) {
		this.firstRowFactor = priority;
	}

	public double getFirstRowFactor() {
		return firstRowFactor;
	}

	public double getFirstRowFactor(int positionY) {
		if (positionY < 0 || positionY > Parameter.numRows) {
			return firstRowFactor;
		} else {
			int distanceToBlackboard = Parameter.numRows - positionY - 1;
			return firstRowFactor * (distanceToBlackboard);
		}
	}

	public Double relationTo(Student student) {
		if (relations.containsKey(student)) {
			return relations.get(student).doubleValue();
		} else {
			return 0.5;
		}
	}

	public void setName(String fName, String lName) {
		firstName = fName;
		lastName = lName;
	}

	public void setRelationTo(Student student, Double value) {
		student.relations.put(this, value);
		relations.put(student, value);
	}

}
