package net.sprauer.sitzplaner.view.Commands;

import java.awt.event.ActionEvent;

import net.sprauer.sitzplaner.model.Student;
import net.sprauer.sitzplaner.view.Dialogs.Dialogs;

public class CommandNewClass extends AbstractCommand {

	private static final long serialVersionUID = 757556993872980074L;

	@Override
	public void actionPerformed(ActionEvent e) {

		int size = Dialogs.getClassSize();
		if (size < 0) {
			return;
		}
		getView().clear();
		getModel().clear();
		for (int i = 0; i < size; i++) {
			getModel().addStudent(new Student((char) (i + 'A') + "", "" + (char) (i + 'A')));
		}
		Factory.CommandNewSeatingPlan.invoke();
	}

	@Override
	public String getButtonCaption() {
		return "Neue Klasse anlegen";
	}

	@Override
	public String getToolTip() {
		return "Erzeugt eine neue Klasse mit der angegebenen Größe";
	}

}
