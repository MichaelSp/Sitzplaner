package net.sprauer.sitzplaner.model.notifications;

public class AbstractNotification {

	private final int studentIndex;

	public AbstractNotification(int index) {
		this.studentIndex = index;
	}

	public int getStudentIndex() {
		return studentIndex;
	}
}
